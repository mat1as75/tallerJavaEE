package org.tallerJava.commerceModule.infrastructure.security.identitystore;

import jakarta.annotation.security.DeclareRoles;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.security.enterprise.credential.UsernamePasswordCredential;
import jakarta.security.enterprise.identitystore.CredentialValidationResult;
import jakarta.security.enterprise.identitystore.DatabaseIdentityStoreDefinition;
import jakarta.security.enterprise.identitystore.IdentityStore;
import jakarta.security.enterprise.credential.Credential;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.repo.CommerceRepository;
import org.tallerJava.commerceModule.infrastructure.security.HashFunctionUtil;
import org.tallerJava.monitoringModule.interfase.event.in.PurchaseModuleObserver;

import java.util.HashSet;
import java.util.Set;

//@DatabaseIdentityStoreDefinition(
//        dataSourceLookup = "java:jboss/MariaDB",
//        callerQuery = "SELECT password FROM commerce_Commerce WHERE rut = ?",
//        groupsQuery = "SELECT group_name FROM commerce_Commerce_Group WHERE commerce_rut = ?"
//)
@ApplicationScoped
@DeclareRoles({"admin", "user"})
public class CredentialValidator implements IdentityStore {
    private static final Logger log = Logger.getLogger(PurchaseModuleObserver.class);

    @PersistenceContext
    private EntityManager em;

//    @Inject
//    private CommerceRepository commerceRepository;
//
//    @Override
//    public CredentialValidationResult validate(Credential credentialParam) {
//        UsernamePasswordCredential credential = (UsernamePasswordCredential) credentialParam;
//
//        String rutStr = credential.getCaller();
//        String password = credential.getPasswordAsString();
//
//        log.info("RUT recibido: " + rutStr);
//
//        try {
//            long rut = Long.parseLong(rutStr);
//            Commerce commerce = commerceRepository.findByRut(rut);
//            log.info("Comercio encontrado: " + commerce.getEmail());
//
//            if (commerce != null && commerce.correctPassword(password)) {
//                log.infof("Comercio %s autenticado correctamente", rut);
//                log.info("Password válida: " + commerce.correctPassword(password));
//
//                Set<String> roles = new HashSet<>();
//                if (commerce.getListGroups() != null) {
//                    commerce.getListGroups().forEach(group -> roles.add(group.getName()));
//                    System.out.println(commerce.getListGroups());
//                }
//
//                CredentialValidationResult validationResult = new CredentialValidationResult(rutStr, roles);
//                log.info("Validation Result: " + validationResult.getStatus() + " " + validationResult.getCallerPrincipal().getName() + " " + validationResult.getCallerGroups() );
//                log.info("Roles asignados: " + validationResult.getCallerGroups());
//                return validationResult;
//            } else {
//                log.errorf("Comercio %s autenticado incorrectamente", rut);
//            }
//        } catch (NumberFormatException e) {
//            log.warn("RUT invalido: " + rutStr);
//        }
//
//        log.errorf("Comercio %s no encontrado", rutStr);
//        return CredentialValidationResult.INVALID_RESULT;
//    }

    @Override
    public CredentialValidationResult validate(Credential credential) {
        System.out.println("** IdentityStore en base de datos");
        CredentialValidationResult resultado = CredentialValidationResult.INVALID_RESULT;

        UsernamePasswordCredential credencial = (UsernamePasswordCredential)credential;
        long usr = Long.parseLong(credencial.getCaller());
        String pass = credencial.getPasswordAsString();
        Commerce userCommerce = findCommerce(usr);
        if (userCommerce != null) {
            System.out.println("encontre usuario: " + userCommerce.getEmail());
            if (userCommerce.correctPassword(pass)) {
                System.out.println("contraseña correcta");
                //al proceso de autorización le interasa saber los grupos a los que pertenece el usuari
                resultado =  new CredentialValidationResult
                        (String.valueOf(usr), new HashSet<>(userCommerce.groupsAsString()));
                System.out.println("RESULTADO: " + resultado.getStatus() + " " + resultado.getCallerPrincipal().getName() + " " + resultado.getCallerGroups());
            } else {
                System.out.println("password incorrecta");
            }
        } else {
            System.out.println("No existe usuario.");
        }
        System.out.println("** Fin IdentityStore en base de datos");
        return resultado;

    }

    private Commerce findCommerce(long rut) { return em.find(Commerce.class, rut); }
}
