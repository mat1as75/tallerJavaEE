package org.tallerJava.purchaseModule.application;

import org.tallerJava.purchaseModule.domain.Card;
import org.tallerJava.purchaseModule.domain.Purchase;

public interface PurchaseService {
    public void processPayment(Purchase purchase, Card card, int rut,int posId);
}