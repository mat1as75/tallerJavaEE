package org.tallerJava.monitoringModule.interfase.event.in;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tallerJava.monitoringModule.application.MonitoringService;
import org.tallerJava.monitoringModule.domain.Payment;
import org.tallerJava.purchaseModule.interfase.event.out.NotifyPayment;

@ApplicationScoped
public class PurchaseModuleObserver {
    private static final Logger log = Logger.getLogger(PurchaseModuleObserver.class);

    @Inject
    private MonitoringService monitoringService;

    public void acceptNotifyPayment(@Observes NotifyPayment event) {
        log.infof("Se realizo un pago: %s", event);
        Payment payment = new Payment(event.getRut_commerce(), event.getAmount(), event.getStatus());

        monitoringService.notifyPayment(payment);
    }

    public void acceptNotifyPaymentOk(@Observes NotifyPayment event) {
        log.infof("Se realizo un pago exitoso: %s", event);
        Payment payment = new Payment(event.getRut_commerce(), event.getAmount(), event.getStatus());

        monitoringService.notifyPaymentOk(payment);
    }

    public void acceptNotifyPaymentFail(@Observes NotifyPayment event) {
        log.infof("Se realizo un pago fallido: %s", event);
        Payment payment = new Payment(event.getRut_commerce(), event.getAmount(), event.getStatus());

        monitoringService.notifyPaymentFail(payment);
    }
}
