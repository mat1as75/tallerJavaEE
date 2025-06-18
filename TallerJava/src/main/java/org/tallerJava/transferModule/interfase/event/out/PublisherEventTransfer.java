package org.tallerJava.transferModule.interfase.event.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.enterprise.event.Event;

@ApplicationScoped
public class PublisherEventTransfer {

    @Inject
    private Event<NotifyDeposit> notifyDepositEvent;

    private NotifyDeposit buildNotifyDepositEvent(long commerceRut, float amount, String accountNumber) {
        return new NotifyDeposit(commerceRut, amount, accountNumber);
    }

    public void publishNotifyDeposit(long commerceRut, float amount, String accountNumber) {
        NotifyDeposit event = buildNotifyDepositEvent(commerceRut, amount, accountNumber);

        notifyDepositEvent.fire(event);
    }
}
