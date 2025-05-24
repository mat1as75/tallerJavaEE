package org.tallerJava.purchaseModule.application;

import org.tallerJava.purchaseModule.application.dto.PaymentDataDTO;
import org.tallerJava.purchaseModule.domain.Card;
import org.tallerJava.purchaseModule.domain.Purchase;
import org.tallerJava.purchaseModule.application.dto.SalesSummaryDTO;

public interface PurchaseService {
    public void processPayment(PaymentDataDTO paymentData);

    public double getTotalSalesAmount(long commerceRut);

    public SalesSummaryDTO getSalesSummaryByPeriod(long rut, String startDate, String endDate);

    public SalesSummaryDTO getSalesSummaryOfTheDay(long commerceRut);
}