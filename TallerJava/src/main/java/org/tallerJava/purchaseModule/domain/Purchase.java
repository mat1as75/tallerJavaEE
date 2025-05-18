package org.tallerJava.purchaseModule.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@Entity
public class Purchase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private float amount;
    private Date date;
    private String description;

    @ManyToOne
    @JoinColumn(name = "commerce_rut", referencedColumnName = "rut")
    private PurchaseCommerce commerce;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "card_number", referencedColumnName = "number")
    private Card card;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "pos_id", referencedColumnName = "id")
    private PurchasePos pos;


    public Purchase(int id, float amount, Date date, String description) {
        this.id = id;
        this.amount = amount;
        this.date = date;
        this.description = description;
    }

    public Purchase() {
    }
}