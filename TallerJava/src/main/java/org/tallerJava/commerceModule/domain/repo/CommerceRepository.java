package org.tallerJava.commerceModule.domain.repo;

import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.Pos;

import java.util.List;

public interface CommerceRepository {
    public Commerce findByRut(int rut);
    public List<Commerce> findAll();
    public boolean create(Commerce commerce);
    public boolean update(Commerce commerce);
    public boolean updatePassword(int rut, String newPass);
    public void delete(int rut);


}
