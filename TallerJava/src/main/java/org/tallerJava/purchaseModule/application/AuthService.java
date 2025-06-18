package org.tallerJava.purchaseModule.application;

import jakarta.enterprise.context.RequestScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.tallerJava.authSharedModule.*;
import org.tallerJava.commerceModule.interfase.event.out.AuthResponse;
import org.tallerJava.purchaseModule.interfase.event.out.AuthRequest;

import java.util.UUID;

@RequestScoped
public class AuthService {

    @Inject
    private Event<AuthRequest> authRequestEvent;

    private AuthResponse latestResponse;

    public AuthResponse authenticate(long commerce_rut, String password) {
        String correlationId = UUID.randomUUID().toString();

        AuthRequest request = new AuthRequest();
        request.setCommerce_rut(commerce_rut);
        request.setPassword(password);
        request.setCorrelationId(correlationId);

        authRequestEvent.fire(request);

        return latestResponse;
    }

    public void receiveAuthResponse(@Observes AuthResponse response) {
        this.latestResponse = response;
    }
}
