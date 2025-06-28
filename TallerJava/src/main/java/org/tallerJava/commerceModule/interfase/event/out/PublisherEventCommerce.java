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
    private Event<CommerceMakeComplaint> makeComplaintEvent;

    @Inject
    private Event<CommerceNewPos> newPosEvent;

    @Inject
    private Event<CommerceNewPos> changePosStatusEvent;

    private CommerceNewCommerce buildCommerceNewCommerceEvent(Commerce commerce) {
        String accountNumber = commerce.getAccount().getAccountNumber();

        Map<Integer, Boolean> mapPos = commerce.getListPos().stream()
                .map(pos -> new CommercePos(pos.getId(), pos.isStatus()))
                .collect(Collectors.toMap(CommercePos::getId, CommercePos::isStatus));

        Map<Integer, String> mapComplaint = commerce.getListComplaints().stream()
                .map(pos -> new CommerceComplaint(pos.getId(), pos.getMessage()))
                .collect(Collectors.toMap(CommerceComplaint::getId, CommerceComplaint::getMessage));

        return new CommerceNewCommerce(
                commerce.getRut(),
                commerce.getEmail(),
                commerce.getPassword(),
                accountNumber,
                mapPos,
                mapComplaint
        );
    }

    public void publishNewCommerce(Commerce commerce) {
        CommerceNewCommerce event = buildCommerceNewCommerceEvent(commerce);

        newCommerceEvent.fire(event);
    }

    public void publishUpdateCommerceData(Commerce commerce) {
        CommerceNewCommerce event = buildCommerceNewCommerceEvent(commerce);

        updateCommerceDataEvent.fire(event);
    }

    public void publishUpdatePasswordCommerce(long rut_commerce, String newPass) {
        CommerceUpdatePassword event = new CommerceUpdatePassword(rut_commerce, newPass);

        updatePasswordEvent.fire(event);
    }

    public void makeCommerceComplaint(long rut_commerce, String message) {
        // Este método solo publica el evento para observadores como el módulo de monitoreo
        CommerceMakeComplaint event = new CommerceMakeComplaint(rut_commerce, message);
        makeComplaintEvent.fire(event);
    }
    
    public void publishNewPos(long rut_commerce, int id_pos, boolean status_pos) {
        CommercePos newPos = new CommercePos(id_pos, status_pos);
        CommerceNewPos event = new CommerceNewPos(rut_commerce, newPos.getId(), newPos.isStatus());

        newPosEvent.fire(event);
    }

    public void publishChangePosStatus(long rut_commerce, int id_pos, boolean status_pos) {
        CommercePos updatedPos = new CommercePos(id_pos, status_pos);
        CommerceNewPos event = new CommerceNewPos(rut_commerce, updatedPos.getId(), updatedPos.isStatus());

        changePosStatusEvent.fire(event);
    }
}
