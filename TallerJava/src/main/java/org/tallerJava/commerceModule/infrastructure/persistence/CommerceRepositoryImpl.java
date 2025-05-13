package org.tallerJava.commerceModule.infrastructure.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;
import org.tallerJava.commerceModule.domain.repo.CommerceRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CommerceRepositoryImpl implements CommerceRepository {

    private final List<Commerce> commercesList = new ArrayList<>();

    public CommerceRepositoryImpl() {
        commercesList.add(new Commerce(1, "comercio1@mail.com", "1", new CommercialBankAccount(1)));
        commercesList.add(new Commerce(2, "comercio2@mail.com", "2", new CommercialBankAccount(2)));
        commercesList.add(new Commerce(3, "comercio3@mail.com", "3", new CommercialBankAccount(3)));
    }

    @Override
    public Commerce findByRut(int rut) {
        for (Commerce c : commercesList) {
            if (c.getRut() == rut) {
                return c;
            }
        }
        return null;
    }

    @Override
    public List<Commerce> findAll() {
        return commercesList;
    }

    @Override
    public int create(Commerce commerce) {
        commercesList.add(commerce);
        return commerce.getRut();
    }

    @Override
    public boolean update(Commerce commerce) {
        Commerce commerceToUpdate = this.findByRut(commerce.getRut());
        if (commerceToUpdate == null) return false;

        commerceToUpdate.setEmail(commerce.getEmail());
        commerceToUpdate.setPassword(commerce.getPassword());
        commerceToUpdate.setAccount(commerce.getAccount());
        return true;
    }

    @Override
    public boolean updatePassword(int rut, String newPass) {
        Commerce commerceToUpdatePass = this.findByRut(rut);
        if (commerceToUpdatePass == null) return false;

        commerceToUpdatePass.setPassword(newPass);
        return true;
    }

    @Override
    public boolean delete(int rut) {
        Commerce commerce = this.findByRut(rut);
        if (commerce == null) return false;

        return commercesList.remove(commerce);
    }
}
