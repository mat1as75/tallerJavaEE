package org.tallerJava.commerceModule.application.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.application.CommerceService;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;
import org.tallerJava.commerceModule.domain.Pos;
import org.tallerJava.commerceModule.domain.repo.CommerceRepository;
import org.tallerJava.commerceModule.infrastructure.security.HashFunctionUtil;
import org.tallerJava.commerceModule.interfase.event.out.PublisherEventCommerce;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CommerceServiceImpl implements CommerceService {
    private static final Logger log = Logger.getLogger(CommerceServiceImpl.class);

    @Inject
    private CommerceRepository commerceRepository;

    @Inject
    private PublisherEventCommerce publisherEventCommerce;

    @Inject
    private org.tallerJava.commerceModule.infrastructure.messaging.ComplaintMessageProducer complaintMessageProducer;

    @Override
    public Commerce getByRut(long rut) {
        return commerceRepository.findByRut(rut);
    }

    @Override
    public Commerce getByEmail(String email) {
        return commerceRepository.findByEmail(email);
    }

    @Override
    public CommercialBankAccount getByCommercialBankAccount(String accountNumber) {
        return commerceRepository.findByCommercialBankAccount(accountNumber);
    }

    @Override
    public List<Commerce> getAll() {
        return commerceRepository.findAll();
    }

    @Override
    public boolean create(Commerce commerce) {
        if (this.getByRut(commerce.getRut()) != null) return false;
        if (this.getByEmail(commerce.getEmail()) != null) return false;
        if (this.getByCommercialBankAccount(commerce.getAccount().getAccountNumber()) != null) return false;

        notifyNewCommerce(commerce);

        String passwordHash = HashFunctionUtil.convertToHas(commerce.getPassword());
        commerce.setPassword(passwordHash);

        List<Pos> listPos = new ArrayList<>(commerce.getListPos());
        if (!listPos.isEmpty()) { // Si contiene algún Pos
            // Notifico un nuevo Pos
            notifyNewPos(commerce.getRut(), listPos.getFirst());
        }
        return commerceRepository.create(commerce);
    }

    private void notifyNewCommerce(Commerce commerce) {
        publisherEventCommerce.publishNewCommerce(commerce);
    }

    @Override
    public boolean update(Commerce commerce) {
        notifyUpdateCommerceData(commerce);
        return commerceRepository.update(commerce);
    }

    private void notifyUpdateCommerceData(Commerce commerce) {
        publisherEventCommerce.publishUpdateCommerceData(commerce);
    }

    @Override
    public boolean updatePassword(long rut, String newPass) {
        String passwordHash = HashFunctionUtil.convertToHas(newPass);

        notifyUpdatePasswordCommerce(rut, passwordHash);
        return commerceRepository.updatePassword(rut, passwordHash);
    }

    private void notifyUpdatePasswordCommerce(long rut_commerce, String newPass) {
        publisherEventCommerce.publishUpdatePasswordCommerce(rut_commerce, newPass);
    }

    @Override
    public boolean delete(long rut) { return commerceRepository.delete(rut); }

    @Override
    public boolean createComplaint(long rut_commerce, String message) {
        try {
            notifyMakeComplaint(rut_commerce, message);

            complaintMessageProducer.sendComplaintMessage(rut_commerce, message);

            log.infof("Queja del comercio %d enviada correctamente", rut_commerce);
            return true;
        } catch (Exception e) {
            log.errorf("Error al crear queja para comercio %d: %s", rut_commerce, e.getMessage());
            return false;
        }
    }

    private void notifyMakeComplaint(long rut_commerce, String message) {
        publisherEventCommerce.makeCommerceComplaint(rut_commerce, message);
    }

    @Override
    public boolean createPos(long rut_commerce, Pos pos) {
        notifyNewPos(rut_commerce, pos);
        return commerceRepository.createPos(rut_commerce, pos);
    }

    private void notifyNewPos(long rut_commerce, Pos pos) {
        publisherEventCommerce.publishNewPos(rut_commerce, pos.getId(), pos.isStatus());
    }

    @Override
    public int changePosStatus(long rut_commerce, Pos pos, boolean newStatus) {
        notifyChangePosStatus(rut_commerce, pos.getId(), newStatus);
        return commerceRepository.changePosStatus(rut_commerce, pos, newStatus);
    }

    private void notifyChangePosStatus(long rut_commerce, int id_pos, boolean newStatus) {
        publisherEventCommerce.publishChangePosStatus(rut_commerce, id_pos, newStatus);
    }
}
