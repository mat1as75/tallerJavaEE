package org.tallerJava.purchaseModule.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Card {
    @Id
    private long number;
    @Getter
    @Setter
    private String brand;
    @Getter
    @Setter
    private String expirationDate;

    public Card(long number, String brand, String expirationDate) {
        this.number = number;
        this.brand = brand;
        this.expirationDate = expirationDate;
    }

    public Card() {
    }

    public long getNro() {
        return number;
    }

    public void setNro(long number) {
        this.number = number;
    }

}
