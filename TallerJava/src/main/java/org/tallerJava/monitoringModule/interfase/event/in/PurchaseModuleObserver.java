package org.tallerJava.monitoringModule.interfase.event.in;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tallerJava.monitoringModule.application.MonitoringService;

@ApplicationScoped
public class PurchaseModuleObserver {
    private static final Logger log = Logger.getLogger(PurchaseModuleObserver.class);

    @Inject
    private MonitoringService monitoringService;

}
