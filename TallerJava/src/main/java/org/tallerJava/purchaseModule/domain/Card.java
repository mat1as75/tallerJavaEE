package org.tallerJava.purchaseModule.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter

public class Card {
    private long number;
    private String brand;
    private String expirationDate;

    public Card(long number, String brand, String expirationDate) {
        this.number = number;
        this.brand = brand;
        this.expirationDate = expirationDate;
    }

    public Card() {
    }
}
