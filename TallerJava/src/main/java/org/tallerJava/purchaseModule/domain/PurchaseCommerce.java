package org.tallerJava.purchaseModule.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "purchase_Commerce")
@Table(name = "purchase_Commerce")
public class PurchaseCommerce {
    @Id
    private int rut;

    @OneToMany(mappedBy = "commerce", cascade = CascadeType.ALL)
    private List<Purchase> purchases = new ArrayList<>();

    public PurchaseCommerce(int rut) {
        this.rut = rut;
    }

    public PurchaseCommerce() {
    }

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
        purchase.setCommerce(this);
    }
    
    public void removePurchase(Purchase purchase) {
        purchases.remove(purchase);
        purchase.setCommerce(null);
    }
}
