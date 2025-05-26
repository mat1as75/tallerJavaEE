package org.tallerJava.purchaseModule.domain;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "purchase_Pos")
@Table(name = "purchase_Pos")
public class PurchasePos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private boolean status;

    public PurchasePos() {}
    public PurchasePos(int id, boolean status) {
        this.id = id;
        this.status = status;
    }
}
