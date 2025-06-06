package org.tallerJava.commerceModule.application;

import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;
import org.tallerJava.commerceModule.domain.Pos;

import java.util.List;

public interface CommerceService {
    public Commerce getByRut(long rut);
    public Commerce getByEmail(String email);
    public CommercialBankAccount getByCommercialBankAccount(int accountNumber);
    public List<Commerce> getAll();

    public boolean create(Commerce commerce);
    public boolean update(Commerce commerce);
    public boolean updatePassword(long rut, String newPass);
    public boolean delete(long rut);

    public boolean createComplaint(long rut_commerce, String message);
    public boolean createPos(long rut_commerce, Pos pos);
    public int changePosStatus(long rut_commerce, Pos pos, boolean status);
}