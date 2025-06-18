package org.tallerJava.monitoringModule.interfase.event.in;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tallerJava.monitoringModule.application.MonitoringService;
import org.tallerJava.monitoringModule.domain.Deposit;
import org.tallerJava.transferModule.interfase.event.out.NotifyDeposit;

@ApplicationScoped
public class TransferModuleObserver {
    private static final Logger log = Logger.getLogger(TransferModuleObserver.class);

    @Inject
    private MonitoringService monitoringService;

    public void acceptNotifyDeposit(@Observes NotifyDeposit event) {
        log.infof("Se realizo una transferencia: %s", event);
        Deposit deposit = new Deposit(event.getCommerceRut(), event.getAmount(), event.getAccountNumber());

        monitoringService.notifyTransfer(deposit);
    }
}
