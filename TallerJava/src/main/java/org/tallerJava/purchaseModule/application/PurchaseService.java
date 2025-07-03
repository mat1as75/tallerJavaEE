package org.tallerJava.purchaseModule.application;

import org.tallerJava.authSharedModule.AuthStatus;
import org.tallerJava.purchaseModule.application.dto.PaymentDataDTO;
import org.tallerJava.purchaseModule.application.dto.SalesSummaryDTO;
import org.tallerJava.purchaseModule.domain.PurchaseCommerce;
import org.tallerJava.purchaseModule.domain.PurchasePos;

public interface PurchaseService {
    public void processPayment(PaymentDataDTO paymentData);

    public double getTotalSalesAmount(long commerceRut);

    public SalesSummaryDTO getSalesSummaryByPeriod(long rut, String startDate, String endDate);

    public SalesSummaryDTO getSalesSummaryOfTheDay(long commerceRut);

    public boolean createCommerce(PurchaseCommerce commerce);

    public boolean createPos(PurchasePos pos);

    public int changePosStatus(PurchasePos pos);

    public AuthStatus isCommerceAuthorized(long commerce_rut, String password);

    public void notifyPaymentError(PaymentDataDTO paymentData);

    public void notifyPaymentSuccess(PaymentDataDTO paymentData);
}