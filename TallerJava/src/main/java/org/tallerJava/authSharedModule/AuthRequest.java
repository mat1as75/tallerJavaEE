package org.tallerJava.authSharedModule;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class AuthRequest implements Serializable {
    private long commerce_rut;
    private String password;
    private String correlationId;
}
