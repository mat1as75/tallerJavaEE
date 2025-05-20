package org.tallerJava.purchaseModule.interfase.remote.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SingleSaleDTO {
    private long id;           // id de la venta (Purchase)
    private double amount;     // Monto de la venta
    private String date;       // Fecha y hora en string (puedes usar formato ISO)
    private String description;// Descripción de la venta
}