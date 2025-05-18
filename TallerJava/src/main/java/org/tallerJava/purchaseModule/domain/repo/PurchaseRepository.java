package org.tallerJava.purchaseModule.domain.repo;

import org.tallerJava.purchaseModule.domain.Purchase;

public interface PurchaseRepository {
    public void create(Purchase purchase);
}