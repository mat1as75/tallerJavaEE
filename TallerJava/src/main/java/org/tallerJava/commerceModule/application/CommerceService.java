package org.tallerJava.commerceModule.application;

import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;
import org.tallerJava.commerceModule.domain.Pos;

import java.util.List;

public interface CommerceService {
    public boolean create(Commerce commerce);
    public boolean update(Commerce commerce);
    public boolean changePassword(int rut, String newPass);
    public void delete(int rut);

    public void makeComplaint(String complaintMessage);

    public Commerce getByRut(int rut);
    public List<Commerce> getAll();

}