package org.tallerJava.purchaseModule.application;

import org.tallerJava.purchaseModule.domain.PurchasePos;
import org.tallerJava.purchaseModule.domain.Card;
import org.tallerJava.purchaseModule.domain.Purchase;
import org.tallerJava.purchaseModule.domain.PurchaseCommerce;
import org.tallerJava.purchaseModule.interfase.remote.rest.dto.SalesSummaryDTO;

public interface PurchaseService {
    public void processPayment(Purchase purchase, Card card, long rut, int posId);

    public double getTotalSalesAmount(long commerceRut);

    public SalesSummaryDTO getSalesSummaryByPeriod(long rut, String startDate, String endDate);

    public SalesSummaryDTO getSalesSummaryOfTheDay(long commerceRut);

    public boolean createCommerce(PurchaseCommerce commerce);

    public boolean createPos(PurchasePos pos);

    public int changePosStatus(PurchasePos pos);

}