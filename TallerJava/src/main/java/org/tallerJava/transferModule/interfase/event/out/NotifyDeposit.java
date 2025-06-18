package org.tallerJava.transferModule.interfase.event.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotifyDeposit {
    private long commerceRut;
    private float amount;
    private String accountNumber;
}
