package org.tallerJava.commerceModule.application.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.application.CommerceService;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.Pos;
import org.tallerJava.commerceModule.domain.repo.CommerceRepository;
import org.tallerJava.commerceModule.interfase.event.out.PublisherEventCommerce;

import java.util.List;


@ApplicationScoped
public class CommerceServiceImpl implements CommerceService {
    private static final Logger log = Logger.getLogger(CommerceServiceImpl.class);

    @Inject
    private CommerceRepository commerceRepository;

    @Inject
    private PublisherEventCommerce publisherEventCommerce;

    @Override
    public boolean create(Commerce commerce) {
        notifyNewCommerce(commerce);
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
    public boolean updatePassword(int rut, String newPass) {
        notifyUpdatePasswordCommerce(rut, newPass);
        return commerceRepository.updatePassword(rut, newPass);
    }

    private void notifyUpdatePasswordCommerce(int rut_commerce, String newPass) {
        publisherEventCommerce.publishUpdatePasswordCommerce(rut_commerce, newPass);
    }

    @Override
    public boolean delete(int rut) { return commerceRepository.delete(rut); }

    @Override
    public boolean createComplaint(int rut_commerce, String message) {
        notifyMakeComplaint(rut_commerce, message);
        return commerceRepository.createComplaint(rut_commerce, message);
    }

    private void notifyMakeComplaint(int rut_commerce, String message) {
        publisherEventCommerce.makeCommerceComplaint(rut_commerce, message);
    }

    @Override
    public boolean createPos(int rut_commerce, Pos pos) {
        notifyNewPos(rut_commerce, pos);
        return commerceRepository.createPos(rut_commerce, pos);
    }

    private void notifyNewPos(int rut_commerce, Pos pos) {
        publisherEventCommerce.publishNewPos(rut_commerce, pos.getId(), pos.isStatus());
    }

    @Override
    public boolean changePosStatus(int rut_commerce, Pos pos, boolean newStatus) {
        notifyChangePosStatus(rut_commerce, pos.getId(), newStatus);
        return commerceRepository.changePosStatus(rut_commerce, pos, newStatus);
    }

    private void notifyChangePosStatus(int rut_commerce, int id_pos, boolean newStatus) {
        publisherEventCommerce.publishChangePosStatus(rut_commerce, id_pos, newStatus);
    }

    @Override
    public Commerce getByRut(int rut) {
        return commerceRepository.findByRut(rut);
    }

    @Override
    public List<Commerce> getAll() {
        return commerceRepository.findAll();
    }

}
