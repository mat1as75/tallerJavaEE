package org.tallerJava.purchaseModule.interfase.event.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.enterprise.event.Event;

@ApplicationScoped
public class PublisherEventPurchase {

    @Inject
    private Event<NotifyPayment> notifyPaymentEvent;

    @Inject
    private Event<NotifySalesReport> notifySalesReportEvent;


    private NotifyPayment buildNotifyPaymentEvent(long rut_commerce, float amount, int status) {
        return new NotifyPayment(rut_commerce, amount, status);
    }

    public void publishNotifyPayment(long rut_commerce, float amount, int status) {
        NotifyPayment event = buildNotifyPaymentEvent(rut_commerce, amount, status);

        notifyPaymentEvent.fire(event);
    }

    private NotifySalesReport buildNotifySalesReportEvent(long rut_commerce) {
        return new NotifySalesReport(rut_commerce);
    }

    public void publishNotifySalesReport(long rut_commerce) {
        NotifySalesReport event = buildNotifySalesReportEvent(rut_commerce);

        notifySalesReportEvent.fire(event);
    }

}
