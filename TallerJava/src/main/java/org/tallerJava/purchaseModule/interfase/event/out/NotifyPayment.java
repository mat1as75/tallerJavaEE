package org.tallerJava.purchaseModule.interfase.event.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NotifyPayment {
    long rut_commerce;
    float amount;
    int status; // 0 = ERROR | 1 = OK
}
