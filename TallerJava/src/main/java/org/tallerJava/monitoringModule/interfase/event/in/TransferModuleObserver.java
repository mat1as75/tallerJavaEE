package org.tallerJava.monitoringModule.interfase.event.in;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tallerJava.monitoringModule.application.MonitoringService;

@ApplicationScoped
public class TransferModuleObserver {
    private static final Logger log = Logger.getLogger(TransferModuleObserver.class);

    @Inject
    private MonitoringService monitoringService;
}
