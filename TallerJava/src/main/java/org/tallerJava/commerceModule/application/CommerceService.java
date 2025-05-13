package org.tallerJava.commerceModule.application;

import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;

import java.util.List;

public interface CommerceService {
    public void create(int rut, String email, String password, CommercialBankAccount account);
    public void update(int rut, String email, String password, CommercialBankAccount account);
    public void changePassword(int rut, String newPass);
    public void delete(int rut);

    public int createPos(Commerce commerce, String posName);
    public void changePosState(Commerce commerce, String posName, boolean state);

    public void makeClaim(String claimText);

    public Commerce getByRut(int rut);
    public List<Commerce> getAll();

}
