package org.tallerJava.monitoringModule.interfase.event.in;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Observes;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tallerJava.monitoringModule.application.MonitoringService;
import org.tallerJava.monitoringModule.domain.Payment;
import org.tallerJava.monitoringModule.domain.SalesReport;
import org.tallerJava.purchaseModule.interfase.event.out.NotifyPayment;
import org.tallerJava.purchaseModule.interfase.event.out.NotifySalesReport;

@ApplicationScoped
public class PurchaseModuleObserver {
    private static final Logger log = Logger.getLogger(PurchaseModuleObserver.class);

    @Inject
    private MonitoringService monitoringService;

    public void acceptNotifyPayment(@Observes NotifyPayment event) {
        Payment payment = new Payment(event.getRut_commerce(), event.getAmount(), event.getStatus());

        if (event.getStatus() == 1) { // Pago OK
            log.infof("Se realizo un pago exitoso: %s", event);
            monitoringService.notifyPaymentOk(payment);
        } else { // Pago FAIL
            log.infof("Se realizo un pago fallido: %s", event);
            monitoringService.notifyPaymentFail(payment);
        }
    }

    public void acceptNotifySalesReport(@Observes NotifySalesReport event) {
        log.infof("Se realizo un reporte de ventas: %s", event);
        SalesReport salesReport = new SalesReport(event.getRut_commerce());

        monitoringService.notifySalesReport(salesReport);
    }
}
