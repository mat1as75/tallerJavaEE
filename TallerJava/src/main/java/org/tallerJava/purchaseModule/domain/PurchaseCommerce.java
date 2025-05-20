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
    private int rut;

    @OneToMany(mappedBy = "commerce", cascade = CascadeType.ALL)
    private List<Purchase> purchases = new ArrayList<>();


    @Transient
    private double totalSalesAmount = 0d;

    public void addPurchaseAmount(double amount,Date purchaseDate, Date dateToday) {
        if (!isSameDay(purchaseDate, dateToday)) {
            this.totalSalesAmount = 0d;
        }
        if (amount > 0) {
            this.totalSalesAmount += amount;
        }
    }

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

    private boolean isSameDay(Date date1, Date date2) {
        LocalDate localDate1 = date1.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate localDate2 = date2.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return localDate1.equals(localDate2);
    }
}
