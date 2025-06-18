package org.tallerJava.commerceModule.interfase.event.out;

import lombok.Getter;
import lombok.Setter;
import org.tallerJava.authSharedModule.AuthStatus;

@Setter
@Getter
public class AuthResponse {
    private String correlationId;
    private AuthStatus status;
}
