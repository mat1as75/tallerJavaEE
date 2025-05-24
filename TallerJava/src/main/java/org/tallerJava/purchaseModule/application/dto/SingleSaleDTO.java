package org.tallerJava.purchaseModule.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleSaleDTO {
    private long id;
    private double amount;
    private String date;
    private String description;
}