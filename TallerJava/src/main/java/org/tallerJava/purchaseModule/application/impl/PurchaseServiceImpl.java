package org.tallerJava.purchaseModule.application.impl;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.tallerJava.authSharedModule.AuthStatus;
import org.tallerJava.purchaseModule.application.AuthService;
import org.tallerJava.purchaseModule.application.PurchaseService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.tallerJava.purchaseModule.application.dto.PaymentDataDTO;
import org.tallerJava.purchaseModule.application.dto.SalesSummaryDTO;
import org.tallerJava.purchaseModule.domain.Card;
import org.tallerJava.purchaseModule.domain.Purchase;

import org.tallerJava.purchaseModule.domain.PurchaseCommerce;
import org.tallerJava.purchaseModule.domain.PurchasePos;
import org.tallerJava.purchaseModule.domain.repo.CommerceRepository;
import org.tallerJava.purchaseModule.domain.repo.PosRepository;
import org.tallerJava.purchaseModule.domain.repo.PurchaseRepository;
import org.tallerJava.purchaseModule.exceptions.PaymentException;
import org.tallerJava.purchaseModule.interfase.event.out.PublisherEventPurchase;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;


@ApplicationScoped
public class PurchaseServiceImpl implements PurchaseService {
    @Inject
    private PurchaseRepository purchaseRepository;
    @Inject
    private CommerceRepository CommerceRepository;
    @Inject
    private PosRepository PosRepository;
    @Inject
    private PublisherEventPurchase publisherEventPurchase;
    @Inject
    private AuthService authService;

    @Override
    @Transactional
    public void processPayment(PaymentDataDTO paymentData) {
        try {
            Purchase purchase = PaymentDataDTO.buildPurchase(paymentData);
            Card card = PaymentDataDTO.buildCard(paymentData.getCardData());
            long rut = paymentData.getCommerceRut();
            int posId = paymentData.getPosId();
            PurchaseCommerce commerce = CommerceRepository.findByRut(rut);
            PurchasePos pos = PosRepository.findById(posId);

            PaymentCommit(paymentData, pos);

            purchase.setPos(pos);
            purchaseRepository.create(purchase);
            commerce.addPurchase(purchase);
            commerce.addPurchaseAmount(purchase.getAmount(), purchase.getDate());
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    @Transactional
    public SalesSummaryDTO getSalesSummaryOfTheDay(long rut) {
        PurchaseCommerce commerce = CommerceRepository.findByRut(rut);

        LocalDate today = LocalDate.now();
        ZoneId zone = ZoneId.systemDefault();
        Date startDay = Date.from(today.atStartOfDay(zone).toInstant());
        Date endDay = Date.from(today.plusDays(1).atStartOfDay(zone).toInstant()); // Paso el rango del día para optimizar consulta

        List<Purchase> todayPurchases = purchaseRepository.findPurchasesByCommerceRutAndDateBetween(commerce, startDay, endDay); // paso commerce para optimizar consulta usando cache hibernate

        this.publisherEventPurchase.publishNotifySalesReport(rut);
        return SalesSummaryDTO.from(todayPurchases);
    }

    @Override
    @Transactional
    public SalesSummaryDTO getSalesSummaryByPeriod(long rut, String startDate, String endDate) {
        PurchaseCommerce commerce = CommerceRepository.findByRut(rut);

        LocalDate start = LocalDate.parse(startDate);
        LocalDate end = LocalDate.parse(endDate);

        ZoneId zone = ZoneId.systemDefault(); // ver de crear helper o similar porque se usa 2 veces
        Date startToDate = Date.from(start.atStartOfDay(zone).toInstant());
        Date endToDate = Date.from(end.plusDays(1).atStartOfDay(zone).toInstant()); // Sumamos uno al dia porque sino lo toma desde las 00 AM  en camibo  si lo  hacemos asi toma hasta(exclusivo) 00 del día siguiente

        List<Purchase> purchases = purchaseRepository.findPurchasesByCommerceRutAndDateBetween(commerce, startToDate, endToDate);

        this.publisherEventPurchase.publishNotifySalesReport(rut);
        return SalesSummaryDTO.from(purchases);
    }

    @Override
    @Transactional
    public double getTotalSalesAmount(long rut) {
        PurchaseCommerce commerce = CommerceRepository.findByRut(rut);
        commerce.resetTotalAmountIfDifferentDay();
        return commerce.getTotalSalesAmount();
    }

    @Override
    @Transactional
    public boolean createCommerce(PurchaseCommerce commerce) {
        return CommerceRepository.create(commerce);
    }

    @Override
    @Transactional
    public boolean createPos(PurchasePos pos) {
        return PosRepository.createPos(pos);
    }

    @Override
    @Transactional
    public int changePosStatus(PurchasePos pos) {
        return PosRepository.changePosStatus(pos);
    }

    private void PaymentCommit(PaymentDataDTO paymentData, PurchasePos pos){
        Client client = ClientBuilder.newClient();
        String url = "http://localhost:8080/PaymentMethod-1.0-SNAPSHOT/api";

        Response response = client
                .target(url)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(paymentData, MediaType.APPLICATION_JSON));

        int status = response.getStatus();
        String responseBody = response.readEntity(String.class);

        if (!pos.isStatus()) {
            this.notifyPaymentError(paymentData);
            throw new IllegalStateException("El POS está inactivo o no disponible");
        }

        switch (status) {
            case 200:
                // Notifico pago OK
                this.notifyPaymentSuccess(paymentData);
                break;
            case 402:
                // Notifico pago FAIL
                this.notifyPaymentError(paymentData);
                throw new PaymentException("Fondos insuficientes: " + responseBody);
            default:
                // Notifico pago FAIL
                this.notifyPaymentError(paymentData);
                throw new PaymentException("Error al procesar medio de pago (código " + status + "): " + responseBody);
        }

        response.close();
        client.close();
    }

    @Override
    public AuthStatus isCommerceAuthorized(long commerce_rut, String password) {
        return authService.authenticate(commerce_rut, password).getStatus();
    }

    @Override
    public void notifyPaymentError(PaymentDataDTO paymentData) {
        publisherEventPurchase.publishNotifyPayment(paymentData.getCommerceRut(), paymentData.getAmount(), 0);
    }

    @Override
    public void notifyPaymentSuccess(PaymentDataDTO paymentData) {
        publisherEventPurchase.publishNotifyPayment(paymentData.getCommerceRut(), paymentData.getAmount(), 1);
    }

}