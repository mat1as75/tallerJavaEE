package org.tallerJava.commerceModule.interfase.event.out;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.enterprise.event.Event;
import jakarta.inject.Inject;
import org.tallerJava.commerceModule.domain.Commerce;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@ApplicationScoped
public class PublisherEventCommerce {

    @Inject
    private Event<CommerceNewCommerce> newCommerceEvent;

    @Inject
    private Event<CommerceNewCommerce> updateCommerceDataEvent;

    @Inject
    private Event<CommerceUpdatePassword> updatePasswordEvent;

    @Inject
    private Event<CommerceMakeClaim> makeComplaintEvent;

    @Inject
    private Event<CommerceNewPos> newPosEvent;

    @Inject
    private Event<CommerceNewPos> changePosStatusEvent;

    public void publishNewCommerce(Commerce commerce) {
        CommerceCommercialBankAccount commerceAccount = new CommerceCommercialBankAccount(commerce.getAccount().getAccountNumber());

        List<CommercePos> commerceListPos = commerce.getListPos().stream()
                .map(pos -> new CommercePos(pos.getId(), pos.isStatus()))
                .toList();
        Map<Integer, Boolean> mapPos = commerceListPos.stream()
                .collect(Collectors.toMap(CommercePos::getId, CommercePos::isStatus));

        List<CommerceComplaint> commerceListComplaint = commerce.getListComplaints().stream()
                .map(pos -> new CommerceComplaint(pos.getId(), pos.getMessage()))
                .toList();
        Map<Integer, String> mapComplaint = commerceListComplaint.stream()
                .collect(Collectors.toMap(CommerceComplaint::getId, CommerceComplaint::getMessage));

        CommerceNewCommerce event = new CommerceNewCommerce(
                commerce.getRut(),
                commerce.getEmail(),
                commerce.getPassword(),
                commerceAccount.getAccountNumber(),
                mapPos,
                mapComplaint
        );

        newCommerceEvent.fire(event);
    }

    public void publishUpdateCommerceData(Commerce commerce) {
        CommerceCommercialBankAccount commerceAccount = new CommerceCommercialBankAccount(commerce.getAccount().getAccountNumber());

        List<CommercePos> commerceListPos = commerce.getListPos().stream()
                .map(pos -> new CommercePos(pos.getId(), pos.isStatus()))
                .toList();
        Map<Integer, Boolean> mapPos = commerceListPos.stream()
                .collect(Collectors.toMap(CommercePos::getId, CommercePos::isStatus));

        List<CommerceComplaint> commerceListComplaint = commerce.getListComplaints().stream()
                .map(pos -> new CommerceComplaint(pos.getId(), pos.getMessage()))
                .toList();
        Map<Integer, String> mapComplaint = commerceListComplaint.stream()
                .collect(Collectors.toMap(CommerceComplaint::getId, CommerceComplaint::getMessage));

        CommerceNewCommerce event = new CommerceNewCommerce(
                (int)commerce.getRut(),
                commerce.getEmail(),
                commerce.getPassword(),
                commerceAccount.getAccountNumber(),
                mapPos,
                mapComplaint
        );

        updateCommerceDataEvent.fire(event);
    }

    public void publishUpdatePasswordCommerce(int rut_commerce, String newPass) {
        CommerceUpdatePassword event = new CommerceUpdatePassword(rut_commerce, newPass);

        updatePasswordEvent.fire(event);
    }

    public void makeCommerceComplaint(int rut_commerce, String message) {
        CommerceMakeClaim event = new CommerceMakeClaim(rut_commerce, message);

        makeComplaintEvent.fire(event);
    }

    public void publishNewPos(int rut_commerce, int id_pos, boolean status_pos) {
        CommercePos newPos = new CommercePos(id_pos, status_pos);
        CommerceNewPos event = new CommerceNewPos(rut_commerce, newPos.getId(), newPos.isStatus());

        newPosEvent.fire(event);
    }

    public void publishChangePosStatus(int rut_commerce, int id_pos, boolean status_pos) {
        CommercePos updatedPos = new CommercePos(id_pos, status_pos);
        CommerceNewPos event = new CommerceNewPos(rut_commerce, updatedPos.getId(), updatedPos.isStatus());

        changePosStatusEvent.fire(event);
    }
}
