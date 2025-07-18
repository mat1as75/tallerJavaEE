package org.tallerJava.monitoringModule.interfase.event.in;

import jakarta.enterprise.context.ApplicationScoped;

import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.interfase.event.out.CommerceMakeComplaint;
import org.tallerJava.monitoringModule.domain.Complaint;
import org.tallerJava.monitoringModule.application.MonitoringService;

@ApplicationScoped
public class CommerceModuleObserver {
    private static final Logger log = Logger.getLogger(CommerceModuleObserver.class);

    @Inject
    private MonitoringService monitoringService;

    public void acceptMakeComplaint(@Observes CommerceMakeComplaint event) {
        log.infof("Nuevo reclamo de Comercio: %s", event);
        Complaint complaint = new Complaint(event.getRut_commerce(), event.getMessage());

        monitoringService.makeComplaint(complaint);
    }
}
