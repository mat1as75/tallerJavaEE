package org.tallerJava.monitoringModule.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Deposit {
    private long commerceRut;
    private float amount;
    private String accountNumber;

    public Deposit() {}
    public Deposit(long commerceRut, float amount, String accountNumber) {
        this.commerceRut = commerceRut;
        this.amount = amount;
        this.accountNumber = accountNumber;
    }
}
