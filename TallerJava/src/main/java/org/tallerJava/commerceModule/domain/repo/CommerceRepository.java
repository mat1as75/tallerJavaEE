package org.tallerJava.commerceModule.domain.repo;

import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.Pos;

import java.util.List;

public interface CommerceRepository {
    public Commerce findByRut(int rut);
    public List<Commerce> findAll();
    public Commerce create(Commerce commerce);
    public Commerce update(Commerce commerce);
    public Commerce updatePassword(int rut, String newPass);
    public void delete(int rut);


}
