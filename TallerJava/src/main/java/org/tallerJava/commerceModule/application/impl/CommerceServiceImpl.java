package org.tallerJava.commerceModule.application.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.application.CommerceService;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;
import org.tallerJava.commerceModule.domain.repo.CommerceRepository;

import java.util.List;


@ApplicationScoped
public class CommerceServiceImpl implements CommerceService {
    private static final Logger log = Logger.getLogger(CommerceServiceImpl.class);

    @Inject
    private CommerceRepository commerceRepository;

    @Override
    public void create(int rut, String email, String password, CommercialBankAccount account) {
        commerceRepository.create(new Commerce(rut, email, password, account));
    }

    @Override
    public void update(int rut, String email, String password, CommercialBankAccount account) {
        commerceRepository.update(new Commerce(rut, email, password, account));
    }

    @Override
    public void changePassword(int rut, String newPass) {
        commerceRepository.updatePassword(rut, newPass);
    }

    @Override
    public void delete(int rut) {
        commerceRepository.delete(rut);
    }

    /* Unfinished */
    @Override
    public int createPos(Commerce commerce, String posName) {
        return 0;
    }

    /* Unfinished */
    @Override
    public void changePosState(Commerce commerce, String posName, boolean state) {

    }

    /* Unfinished */
    @Override
    public void makeClaim(String claimText) {
        log.infof("Claim text: %s", claimText);
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
