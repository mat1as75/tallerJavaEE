package org.tallerJava.purchaseModule.application.impl;

import jakarta.transaction.Transactional;
import org.tallerJava.purchaseModule.application.PurchaseService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.tallerJava.purchaseModule.application.dto.PaymentDataDTO;
import org.tallerJava.purchaseModule.domain.Card;
import org.tallerJava.purchaseModule.domain.Purchase;

import org.tallerJava.purchaseModule.domain.PurchaseCommerce;
import org.tallerJava.purchaseModule.domain.PurchasePos;
import org.tallerJava.purchaseModule.domain.repo.CommerceRepository;
import org.tallerJava.purchaseModule.domain.repo.PosRepository;
import org.tallerJava.purchaseModule.domain.repo.PurchaseRepository;
import org.tallerJava.purchaseModule.application.dto.SalesSummaryDTO;

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
        //Falta agregar validación de la tarjeta frente a Medios de pago
        purchase.setPos(pos);
        purchaseRepository.create(purchase);
        commerce.addPurchase(purchase);
        commerce.addPurchaseAmount(purchase.getAmount(), purchase.getDate());
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

}