package org.tallerJava.purchaseModule.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@Entity(name = "purchase_Commerce")
@Table(name = "purchase_Commerce")
public class PurchaseCommerce {
    @Id
    private long rut;

    @OneToMany(mappedBy = "commerce", cascade = CascadeType.ALL)
    private List<Purchase> purchases = new ArrayList<>();

    private Date lastPurchase;

    private double totalSalesAmount = 0d;

    public PurchaseCommerce(long rut) {
        this.rut = rut;
    }

    public PurchaseCommerce() {
    }

    public void addPurchaseAmount(double amount,Date purchaseDate) {
        if (isDifferentDay(purchaseDate, lastPurchase)) {
            this.totalSalesAmount = 0d;
        }
        if (amount > 0) {
            this.totalSalesAmount += amount;
        }
    }
    public void resetTotalAmountIfDifferentDay(){
        if (isDifferentDay(lastPurchase, new Date())) {
            this.totalSalesAmount = 0d;
        }
    }

    public void addPurchase(Purchase purchase) {
        purchases.add(purchase);
        purchase.setCommerce(this);
        this.lastPurchase = purchase.getDate();
    }
    
    public void removePurchase(Purchase purchase) {
        purchases.remove(purchase);
        purchase.setCommerce(null);
    }

    private boolean isDifferentDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return true;
        }
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return !localDate1.equals(localDate2);
    }
}
