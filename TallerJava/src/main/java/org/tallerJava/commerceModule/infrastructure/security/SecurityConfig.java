package org.tallerJava.commerceModule.infrastructure.security;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.security.enterprise.authentication.mechanism.http.BasicAuthenticationMechanismDefinition;

@BasicAuthenticationMechanismDefinition(realmName = "ApplicationRealm")
@ApplicationScoped
public class SecurityConfig {
}
