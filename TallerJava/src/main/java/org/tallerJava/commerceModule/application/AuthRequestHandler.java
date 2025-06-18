package org.tallerJava.commerceModule.application;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.tallerJava.authSharedModule.*;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.interfase.event.out.AuthResponse;
import org.tallerJava.purchaseModule.interfase.event.out.AuthRequest;

@ApplicationScoped
public class AuthRequestHandler {

    @Inject
    private Event<AuthResponse> authResponseEvent;

    @Inject
    private CommerceService commerceService;

    public void onAuthRequest(@Observes AuthRequest request) {
        AuthStatus status;

        Commerce commerce = commerceService.getByRut(request.getCommerce_rut());
        if (commerce != null) {
            if (commerce.correctPassword(request.getPassword()))
                status = AuthStatus.OK_AUTH;
            else
                status = AuthStatus.NOT_AUTH;
        } else {
            status = AuthStatus.NOT_EXISTS;
        }

        AuthResponse response = new AuthResponse();
        response.setCorrelationId(request.getCorrelationId());
        response.setStatus(status);

        authResponseEvent.fire(response);
    }
}
