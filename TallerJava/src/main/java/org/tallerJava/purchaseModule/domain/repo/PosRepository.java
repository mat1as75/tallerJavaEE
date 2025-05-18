package org.tallerJava.purchaseModule.domain.repo;

import org.tallerJava.purchaseModule.domain.PurchasePos;

public interface PosRepository {
    public PurchasePos findById(int id);
}
