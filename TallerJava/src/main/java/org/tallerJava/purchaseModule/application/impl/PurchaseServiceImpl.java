package org.tallerJava.purchaseModule.application.impl;

import jakarta.transaction.Transactional;
import jakarta.ws.rs.client.Client;
import jakarta.ws.rs.client.ClientBuilder;
import jakarta.ws.rs.client.Entity;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
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

    @Override
    @Transactional
    public void processPayment(PaymentDataDTO paymentData) {
        Purchase purchase = PaymentDataDTO.buildPurchase(paymentData);
        Card card = PaymentDataDTO.buildCard(paymentData.getCardData());
        long rut = paymentData.getCommerceRut();
        int posId = paymentData.getPosId();

        PurchaseCommerce commerce = CommerceRepository.findByRut(rut);
        PurchasePos pos = PosRepository.findById(posId);
        if (!pos.isStatus()) {
            throw new IllegalStateException("El POS está inactivo o no disponible");
        }

        PaymentCommit(paymentData);

        purchase.setPos(pos);
        purchaseRepository.create(purchase);
        commerce.addPurchase(purchase);
        commerce.addPurchaseAmount(purchase.getAmount(), purchase.getDate());
    }

    private void notifyPayment(long rut_commerce, float amount, int status) {
        publisherEventPurchase.publishNotifyPayment(rut_commerce, amount, status);
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

    private void PaymentCommit(PaymentDataDTO paymentData){
        Client client = ClientBuilder.newClient();
        String url = "http://localhost:8080/PaymentMethod-1.0-SNAPSHOT/api";

        Response response = client
                .target(url)
                .request(MediaType.APPLICATION_JSON)
                .post(Entity.entity(paymentData, MediaType.APPLICATION_JSON));

        if (response.getStatus() == 200) {
            System.out.println("Respuesta backend PaymentMethod: " + response.readEntity(String.class));
        } else {
            String errorMsg = response.readEntity(String.class);
            System.err.println("Error al procesar medio de pago: " + errorMsg);
        }

        response.close();
        client.close();
    }

}