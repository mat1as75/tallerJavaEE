package org.tallerJava.commerceModule.application.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.application.CommerceService;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;
import org.tallerJava.commerceModule.domain.Pos;
import org.tallerJava.commerceModule.domain.repo.CommerceRepository;

import java.util.ArrayList;
import java.util.List;


@ApplicationScoped
public class CommerceServiceImpl implements CommerceService {
    private static final Logger log = Logger.getLogger(CommerceServiceImpl.class);

    @Inject
    private CommerceRepository commerceRepository;

    @Override
    public Commerce create(Commerce commerce) {
        return commerceRepository.create(commerce);
    }

    @Override
    public Commerce update(Commerce commerce) {
        return commerceRepository.update(commerce);
    }

    @Override
    public Commerce changePassword(int rut, String newPass) {
        return commerceRepository.updatePassword(rut, newPass);
    }

    @Override
    public void delete(int rut) {
        commerceRepository.delete(rut);
    }

    /* Unfinished */
    @Override
    public void makeComplaint(String complaintMessage) {
        log.infof("Complaint message: %s", complaintMessage);
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
