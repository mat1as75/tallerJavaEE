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
    public boolean delete(int rut);

    public boolean createComplaint(int rut_commerce, String message);
    public boolean createPos(int rut_commerce, Pos pos);
    public boolean changePosStatus(int rut_commerce, Pos pos, boolean status);

}
