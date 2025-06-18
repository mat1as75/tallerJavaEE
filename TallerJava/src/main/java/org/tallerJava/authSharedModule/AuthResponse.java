package org.tallerJava.authSharedModule;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
public class AuthResponse implements Serializable {
    private String correlationId;
    private AuthStatus status;
}
