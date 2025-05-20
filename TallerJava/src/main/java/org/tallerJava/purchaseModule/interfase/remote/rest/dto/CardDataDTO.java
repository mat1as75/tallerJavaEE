package org.tallerJava.purchaseModule.interfase.remote.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CardDataDTO {
    private int number;
    private String brand;
    private String date;

    public CardDataDTO() {
    }

}
