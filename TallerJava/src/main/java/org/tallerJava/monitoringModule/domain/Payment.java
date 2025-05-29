package org.tallerJava.monitoringModule.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Payment {
    long rut_commerce;
    float amount;
    int status;

    public Payment() {}
    public Payment(long rut_commerce, float amount, int status) {
        this.rut_commerce = rut_commerce;
        this.amount = amount;
        this.status = status;
    }
}
