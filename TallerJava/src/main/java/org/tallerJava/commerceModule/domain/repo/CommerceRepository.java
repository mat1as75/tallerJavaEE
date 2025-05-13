package org.tallerJava.commerceModule.domain.repo;

import org.tallerJava.commerceModule.domain.Commerce;

import java.util.List;

public interface CommerceRepository {
    public Commerce findByRut(int rut);
    public List<Commerce> findAll();
    public int create(Commerce commerce);
    public boolean update(Commerce commerce);
    public boolean updatePassword(int rut, String newPass);
    public boolean delete(int rut);
}
