package org.tallerJava.commerceModule.application;

import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;
import org.tallerJava.commerceModule.domain.Pos;

import java.util.List;

public interface CommerceService {
    public boolean create(Commerce commerce);
    public boolean update(Commerce commerce);
    public boolean updatePassword(int rut, String newPass);
    public boolean delete(int rut);

    public boolean createComplaint(int rut_commerce, String message);
    public boolean createPos(int rut_commerce, Pos pos);
    public boolean changePosStatus(int rut_commerce, Pos pos, boolean status);


    public Commerce getByRut(int rut);
    public List<Commerce> getAll();

}