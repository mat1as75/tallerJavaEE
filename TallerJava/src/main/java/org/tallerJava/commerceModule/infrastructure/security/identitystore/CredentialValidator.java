package org.tallerJava.commerceModule.infrastructure.security.identitystore;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.credential.Credential;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.monitoringModule.interfase.event.in.PurchaseModuleObserver;
import java.util.HashSet;

@ApplicationScoped
public class CredentialValidator implements IdentityStore {
    private static final Logger log = Logger.getLogger(PurchaseModuleObserver.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    public CredentialValidationResult validate(Credential credential) {
        System.out.println("** IdentityStore en base de datos");
        CredentialValidationResult result = CredentialValidationResult.INVALID_RESULT;

        UsernamePasswordCredential credencial = (UsernamePasswordCredential)credential;
        long usr = Long.parseLong(credencial.getCaller());
        String pass = credencial.getPasswordAsString();
        Commerce userCommerce = findCommerce(usr);
        if (userCommerce != null) {
            log.info("Usuario encontrado: " + userCommerce.getEmail());
            if (userCommerce.correctPassword(pass)) {
                log.info("contraseña correcta");
                result =  new CredentialValidationResult
                        (String.valueOf(usr), new HashSet<>(userCommerce.groupsAsString()));
            } else {
                log.errorf("Password incorrecta.");
            }
        } else {
            log.errorf("No existe usuario.");
        }
        return result;
    }

    private Commerce findCommerce(long rut) { return em.find(Commerce.class, rut); }
}
