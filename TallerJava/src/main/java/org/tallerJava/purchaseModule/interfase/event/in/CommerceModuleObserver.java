package org.tallerJava.purchaseModule.interfase.event.in;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.interfase.event.out.CommerceNewCommerce;
import org.tallerJava.commerceModule.interfase.event.out.CommerceNewPos;
import org.tallerJava.purchaseModule.application.PurchaseService;
import org.tallerJava.purchaseModule.domain.PurchaseCommerce;
import org.tallerJava.purchaseModule.domain.PurchasePos;

@ApplicationScoped
public class CommerceModuleObserver {
    private static final Logger log = Logger.getLogger(CommerceModuleObserver.class);

    @Inject
    private PurchaseService purchaseService;

    public void acceptNewCommerce(@Observes CommerceNewCommerce event) {
        log.infof("Nuevo Comercio: %s", event);
        PurchaseCommerce newCommerce = new PurchaseCommerce(event.getRut());

        purchaseService.createCommerce(newCommerce);
    }

    public void acceptNewPos(@Observes CommerceNewPos event) {
        log.infof("Nuevo Comercio: %s", event);
        PurchasePos newPos = new PurchasePos(event.getId_pos(), event.isStatus());

        purchaseService.createPos(newPos);
    }

    public void acceptChangePosStatus(@Observes CommerceNewPos event) {
        log.infof("Cambio Estado Pos: %s", event);
        PurchasePos newPos = new PurchasePos(event.getId_pos(), event.isStatus());

        purchaseService.changePosStatus(newPos);
    }

}
