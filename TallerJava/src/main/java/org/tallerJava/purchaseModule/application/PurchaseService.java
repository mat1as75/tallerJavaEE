package org.tallerJava.purchaseModule.application;

import org.tallerJava.purchaseModule.domain.Card;
import org.tallerJava.purchaseModule.domain.Purchase;
import org.tallerJava.purchaseModule.interfase.remote.rest.dto.SalesSummaryDTO;

import java.util.List;

public interface PurchaseService {
    public void processPayment(Purchase purchase, Card card, int rut, int posId);

    public List<Purchase> getPurchasesOfTheDay(int commerceRut);
    public double getTotalSalesAmount(int commerceRut);
    
    /**
     * Obtiene un resumen de ventas del día para un comercio, incluyendo todas las transacciones
     * y el monto total de ventas en un DTO estructurado.
     * 
     * @param commerceRut El RUT del comercio
     * @return DTO con el resumen de ventas del día
     */
    public SalesSummaryDTO getSalesSummaryOfTheDay(int commerceRut);
}