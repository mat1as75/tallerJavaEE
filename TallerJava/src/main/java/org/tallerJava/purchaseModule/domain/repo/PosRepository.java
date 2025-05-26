package org.tallerJava.purchaseModule.domain.repo;

import org.tallerJava.purchaseModule.domain.PurchaseCommerce;
import org.tallerJava.purchaseModule.domain.PurchasePos;

public interface PosRepository {
    public PurchasePos findById(int id);

    public boolean createPos(PurchasePos pos);

    public int changePosStatus(PurchasePos pos);

}
