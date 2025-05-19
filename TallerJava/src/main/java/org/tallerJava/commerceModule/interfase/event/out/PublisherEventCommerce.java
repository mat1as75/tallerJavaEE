package org.tallerJava.commerceModule.interfase.event.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.tallerJava.commerceModule.domain.Commerce;

import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class PublisherEventCommerce {

    @Inject
    private Event<CommerceCommercialBankAccount> commercialBankAccount;

    @Inject
    private Event<CommercePos> pos;

    @Inject
    private Event<CommerceComplaint> complaint;

    @Inject
    private Event<CommerceNewCommerce> newCommerce;

    //public void publishNewPos(Pos pos)

    public void pubishNewCommerce(Commerce commerce) {
        CommerceCommercialBankAccount commerceAccount = new CommerceCommercialBankAccount(commerce.getAccount().getAccountNumber());

        List<CommercePos> commerceListPos = commerce.getListPos().stream()
                .map(pos -> new CommercePos(pos.getId(), pos.isStatus()))
                .collect(Collectors.toList());

        List<CommerceComplaint> commerceListComplaint = commerce.getListComplaints().stream()
                .map(pos -> new CommerceComplaint(pos.getId(), pos.getMessage()))
                .collect(Collectors.toList());

        CommerceNewCommerce event = new CommerceNewCommerce(
                (int)commerce.getRut(),
                commerce.getEmail(),
                commerce.getPassword(),
                commerceAccount,
                commerceListPos,
                commerceListComplaint
        );

        newCommerce.fire(event);
    }
}
