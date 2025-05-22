package org.tallerJava.purchaseModule.domain.repo;

import org.tallerJava.purchaseModule.domain.Purchase;
import org.tallerJava.purchaseModule.domain.PurchaseCommerce;

import java.util.Date;
import java.util.List;

public interface PurchaseRepository {
    public void create(Purchase purchase);
    public List<Purchase> findPurchasesByCommerceRutAndDateBetween(PurchaseCommerce commerce, Date start, Date end);
}