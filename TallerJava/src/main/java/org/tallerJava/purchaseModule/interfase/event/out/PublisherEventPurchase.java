package org.tallerJava.purchaseModule.interfase.event.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.enterprise.event.Event;

@ApplicationScoped
public class PublisherEventPurchase {

    @Inject
    private Event<NotifyPayment> notifyPaymentEvent;

    @Inject
    private Event<NotifyPayment> notifyPaymentOkEvent;

    @Inject
    private Event<NotifyPayment> notifyPaymentFailEvent;

    private NotifyPayment buildNotifyPaymentEvent(long rut_commerce, float amount, int status) {
        return new NotifyPayment(rut_commerce, amount, status);
    }

    public void publishNotifyPayment(long rut_commerce, float amount, int status) {
        NotifyPayment event = buildNotifyPaymentEvent(rut_commerce, amount, status);

        notifyPaymentEvent.fire(event);
    }

    private NotifyPayment buildNotifyPaymentOkEvent(long rut_commerce, float amount, int status) {
        return new NotifyPayment(rut_commerce, amount, status);
    }

    public void publishNotifyOkPayment(long rut_commerce, float amount, int status) {
        NotifyPayment event = buildNotifyPaymentOkEvent(rut_commerce, amount, status);

        notifyPaymentOkEvent.fire(event);
    }

    private NotifyPayment buildNotifyPaymentFailEvent(long rut_commerce, float amount, int status) {
        return new NotifyPayment(rut_commerce, amount, status);
    }

    public void publishNotifyFailPayment(long rut_commerce, float amount, int status) {
        NotifyPayment event = buildNotifyPaymentFailEvent(rut_commerce, amount, status);

        notifyPaymentFailEvent.fire(event);
    }

}
