package org.tallerJava.transferModule.application.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransferDetailsDTO {
    private long commerceRut;
    private float amount;
    private String accountNumber;

    public TransferDetailsDTO() {}

    public TransferDetailsDTO(long commerceRut, float amount, String accountNumber) {
        this.commerceRut = commerceRut;
        this.amount = amount;
        this.accountNumber = accountNumber;
    }
}
