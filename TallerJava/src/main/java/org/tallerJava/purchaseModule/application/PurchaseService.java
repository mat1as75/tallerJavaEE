package org.tallerJava.purchaseModule.application;

import org.tallerJava.purchaseModule.domain.PurchasePos;
import org.tallerJava.purchaseModule.domain.Card;
import org.tallerJava.purchaseModule.domain.Purchase;
import org.tallerJava.purchaseModule.domain.PurchaseCommerce;
import org.tallerJava.purchaseModule.interfase.remote.rest.dto.SalesSummaryDTO;

public interface PurchaseService {
    public void processPayment(Purchase purchase, Card card, int rut, int posId);

    public double getTotalSalesAmount(int commerceRut);

    public SalesSummaryDTO getSalesSummaryByPeriod(int rut, String startDate, String endDate);

    public SalesSummaryDTO getSalesSummaryOfTheDay(int commerceRut);

    public boolean createCommerce(PurchaseCommerce commerce);

    public boolean createPos(long rut_commerce, PurchasePos pos);

    public int changePosStatus(long rut_commerce, PurchasePos pos, boolean status);

    public PurchaseCommerce getByRut(long rut);
}