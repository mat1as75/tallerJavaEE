package org.tallerJava.purchaseModule.application;

import org.tallerJava.purchaseModule.domain.Card;
import org.tallerJava.purchaseModule.domain.Purchase;
import org.tallerJava.purchaseModule.interfase.remote.rest.dto.SalesSummaryDTO;

public interface PurchaseService {
    public void processPayment(Purchase purchase, Card card, int rut, int posId);

    public double getTotalSalesAmount(int commerceRut);

    public SalesSummaryDTO getSalesSummaryOfTheDay(int commerceRut);
}