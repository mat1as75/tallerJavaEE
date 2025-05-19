package org.tallerJava.commerceModule.infrastructure.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;
import org.tallerJava.commerceModule.domain.Complaint;
import org.tallerJava.commerceModule.domain.Pos;
import org.tallerJava.commerceModule.domain.repo.CommerceRepository;

import java.util.ArrayList;
import java.util.List;

@ApplicationScoped
public class CommerceRepositoryImpl implements CommerceRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public Commerce findByRut(int rut) {
        try {
            String query = "SELECT c FROM commerce_Commerce c " +
                        "LEFT JOIN FETCH c.listComplaints " +
                        "LEFT JOIN FETCH c.listPos " +
                        "WHERE c.rut = :rut";

            return em.createQuery(query, Commerce.class)
                    .setParameter("rut", rut)
                    .getSingleResult();
        } catch (NoResultException e) {
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
        boolean isPersistedSuccessfully = false;

        if (this.findByRut(commerce.getRut()) != null) return false;

        try {
            em.persist(commerce.getAccount());
            List<Pos> listPos = new ArrayList<>(commerce.getListPos());
            List<Complaint> listComplaints = new ArrayList<>(commerce.getListComplaints());
            for (Pos p : listPos) {
                em.persist(p);
            }
            for (Complaint c : listComplaints) {
                em.persist(c);
            }
            em.merge(commerce);
            isPersistedSuccessfully = true;
        } catch (PersistenceException e) {
            throw new PersistenceException("Error al persistir el comercio", e);
        }
        return isPersistedSuccessfully;
    }

    @Override
    public boolean update(Commerce commerce) {
        try {
            return em.merge(commerce) != null;
        } catch (PersistenceException e) {
            return false;
        }
    }

    @Override
    public boolean updatePassword(int rut, String newPass) {
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
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public void delete(int rut) {
        em.remove(this.findByRut(rut));
    }
}
