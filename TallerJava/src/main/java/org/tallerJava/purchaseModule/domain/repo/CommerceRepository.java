package org.tallerJava.purchaseModule.domain.repo;

import org.tallerJava.purchaseModule.domain.PurchaseCommerce;

public interface CommerceRepository {
    public PurchaseCommerce findByRut(long rut);

    public boolean create(PurchaseCommerce commerce);
}
