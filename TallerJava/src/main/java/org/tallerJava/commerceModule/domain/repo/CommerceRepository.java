package org.tallerJava.commerceModule.domain.repo;

import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;
import org.tallerJava.commerceModule.domain.Pos;

import java.util.List;

public interface CommerceRepository {
    public Commerce findByRut(long rut);
    public Commerce findByEmail(String email);
    public CommercialBankAccount findByCommercialBankAccount(String accountNumber);
    public List<Commerce> findAll();
    public boolean create(Commerce commerce);
    public boolean update(Commerce commerce);
    public boolean updatePassword(long rut, String newPass);
    public boolean delete(long rut);

    public void createComplaint(long rut_commerce, String message, String qualification);
    public boolean createPos(long rut_commerce, Pos pos);
    public int changePosStatus(long rut_commerce, Pos pos, boolean status);

}
