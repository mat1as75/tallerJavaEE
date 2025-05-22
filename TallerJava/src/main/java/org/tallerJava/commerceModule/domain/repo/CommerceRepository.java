package org.tallerJava.commerceModule.domain.repo;

import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.Pos;

import java.util.List;

public interface CommerceRepository {
    public Commerce findByRut(long rut);
    public List<Commerce> findAll();
    public boolean create(Commerce commerce);
    public boolean update(Commerce commerce);
    public boolean updatePassword(long rut, String newPass);
    public boolean delete(long rut);

    public boolean createComplaint(long rut_commerce, String message);
    public boolean createPos(long rut_commerce, Pos pos);
    public boolean changePosStatus(long rut_commerce, Pos pos, boolean status);

}
