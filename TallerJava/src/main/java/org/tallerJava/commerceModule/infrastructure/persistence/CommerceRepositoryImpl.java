package org.tallerJava.commerceModule.infrastructure.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;
import org.tallerJava.commerceModule.domain.Complaint;
import org.tallerJava.commerceModule.domain.Pos;
import org.tallerJava.commerceModule.domain.repo.CommerceRepository;
import org.tallerJava.commerceModule.infrastructure.security.identitystore.CredentialValidator;

import java.util.List;

@ApplicationScoped
public class CommerceRepositoryImpl implements CommerceRepository {

    @PersistenceContext
    private EntityManager em;

    @Inject
    private CredentialValidator identityStore;

    private static final Logger log = Logger.getLogger(CommerceRepositoryImpl.class);

    @Override
    @Transactional
    public Commerce findByRut(long rut) {
        try {
            String query = "SELECT c FROM commerce_Commerce c " +
                    "LEFT JOIN FETCH c.listComplaints " +
                    "LEFT JOIN FETCH c.listPos " +
                    "WHERE c.rut = :rut";

            List<Commerce> commerce = em.createQuery(query, Commerce.class)
                    .setParameter("rut", rut)
                    .getResultList();

            return commerce.isEmpty() ? null : commerce.getFirst();
        } catch (NoResultException e) {
            log.info("No se encontró comercio con rut: " + rut);
            return null;
        }
    }

    @Override
    @Transactional
    public Commerce findByEmail(String email) {
        try {
            String query = "SELECT c FROM commerce_Commerce c " +
                    "LEFT JOIN FETCH c.listComplaints " +
                    "LEFT JOIN FETCH c.listPos " +
                    "WHERE c.email = :email";

            List<Commerce> commerce = em.createQuery(query, Commerce.class)
                    .setParameter("email", email)
                    .getResultList();

            return commerce.isEmpty() ? null : commerce.getFirst();
        } catch (NoResultException e) {
            log.info("No se encontró comercio con email: " + email);
            return null;
        }
    }

    @Override
    @Transactional
    public CommercialBankAccount findByCommercialBankAccount(int accountNumber) {
        try {
            String query = "SELECT cba FROM commerce_CommercialBankAccount cba " +
                    "WHERE cba.accountNumber = :accountNumber";

            List<CommercialBankAccount> commercialBankAccount = em.createQuery(query, CommercialBankAccount.class)
                    .setParameter("accountNumber", accountNumber)
                    .getResultList();

            return commercialBankAccount.isEmpty() ? null : commercialBankAccount.getFirst();
        } catch (NoResultException e) {
            log.info("No se encontró numero de cuenta con numero: " + accountNumber);
            return null;
        }
    }

    @Override
    @Transactional
    public List<Commerce> findAll() {
        String query1 = "SELECT DISTINCT c FROM commerce_Commerce c LEFT JOIN FETCH c.listPos";
        List<Commerce> commerces = em.createQuery(query1, Commerce.class).getResultList();

        // Cargar las que falten por separado
        for (Commerce commerce : commerces) {
            String query2 = "SELECT c FROM commerce_Commerce c LEFT JOIN FETCH c.listComplaints WHERE c.rut = :rut";
            Commerce full = em.createQuery(query2, Commerce.class)
                    .setParameter("rut", commerce.getRut())
                    .getSingleResult();
            commerce.setListComplaints(full.getListComplaints());
        }

        return commerces;
    }

    @Override
    public boolean create(Commerce commerce) {
        try {
            em.persist(commerce);
            return true;
        } catch (PersistenceException e) {
            throw e;
        }
    }

    @Override
    public boolean update(Commerce commerce) {
        if (commerce.getRut() != 0)
            if (this.findByRut(commerce.getRut()) == null) return false;

        try {
            return em.merge(commerce) != null;
        } catch (PersistenceException e) {
            return false;
        }
    }

    @Override
    public boolean updatePassword(long rut, String newPass) {
        Commerce commerceToUpdatePass = this.findByRut(rut);
        if (commerceToUpdatePass == null) return false;

        String query = "UPDATE commerce_Commerce c SET c.password = :newPass WHERE c.rut = :rut";
        try {
            Query updatePassword = em.createQuery(query);
            updatePassword.setParameter("newPass", newPass);
            updatePassword.setParameter("rut", rut);
            updatePassword.executeUpdate();
            em.flush();

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean delete(long rut) {
        Commerce commerce = this.findByRut(rut);
        if (commerce == null) return false;

        try {
            em.remove(this.findByRut(rut));
            return true;
        } catch (PersistenceException e) {
            return false;
        }
    }

    @Override
    public boolean createComplaint(long rut_commerce, String message) {
        Commerce commerce = this.findByRut(rut_commerce);
        if (commerce == null) return false;
        Complaint complaint = new Complaint(message);

        try {
            em.persist(complaint);
            commerce.getListComplaints().add(complaint);
            em.merge(commerce);
            return true;
        } catch (PersistenceException e) {
            System.out.println("Fallo al crear el reclamo en CommerceRepositoryImpl.createComplaint()");
            return false;
        }
    }

    @Override
    public boolean createPos(long rut_commerce, Pos pos) {
        Commerce commerce = this.findByRut(rut_commerce);
        if (commerce == null) return false;

        try {
            Pos newPos = new Pos(pos.getId(), pos.isStatus());
            commerce.getListPos().add(newPos);
            em.merge(commerce);
            return true;
        } catch (PersistenceException e) {
            return false;
        }
    }

    @Override
    public int changePosStatus(long rut_commerce, Pos pos, boolean newStatus) {
        Commerce commerce = this.findByRut(rut_commerce);
        if (commerce == null) return -1;

        try {
            Pos updatePos = commerce.getListPos().stream()
                    .filter(p -> p.getId() == pos.getId())
                    .findFirst()
                    .orElse(null);

            if (updatePos == null) return -2;

            updatePos.setStatus(newStatus);
            em.merge(commerce);
            return 1;
        } catch (PersistenceException e) {
            return 0;
        }
    }
}
