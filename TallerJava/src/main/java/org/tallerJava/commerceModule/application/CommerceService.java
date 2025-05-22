package org.tallerJava.commerceModule.application;

import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;
import org.tallerJava.commerceModule.domain.Pos;

import java.util.List;

public interface CommerceService {
    public boolean create(Commerce commerce);
    public boolean update(Commerce commerce);
    public boolean updatePassword(long rut, String newPass);
    public boolean delete(long rut);

    public boolean createComplaint(long rut_commerce, String message);
    public boolean createPos(long rut_commerce, Pos pos);
    public boolean changePosStatus(long rut_commerce, Pos pos, boolean status);


    public Commerce getByRut(long rut);
    public List<Commerce> getAll();

}