package org.tallerJava.purchaseModule.interfase.event.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class AuthRequest {
    private long commerce_rut;
    private String password;
    private String correlationId;
}
