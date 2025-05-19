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
        // Cargar el comercio con la primera colección
        String query1 = "SELECT c FROM commerce_Commerce c LEFT JOIN FETCH c.listPos WHERE c.rut = :rut";
        Commerce commerce = em.createQuery(query1, Commerce.class)
                .setParameter("rut", rut)
                .getSingleResult();

        // Cargar la segunda colección por separado
        String query2 = "SELECT c FROM commerce_Commerce c LEFT JOIN FETCH c.listComplaints WHERE c.rut = :rut";
        commerce = em.createQuery(query2, Commerce.class)
                .setParameter("rut", rut)
                .getSingleResult();

        return commerce;
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
    public Commerce create(Commerce commerce) {
        em.persist(commerce.getAccount());
        List<Pos> listPos = new ArrayList<>(commerce.getListPos());
        List<Complaint> listComplaints = new ArrayList<>(commerce.getListComplaints());
        for (Pos p : listPos) {
            em.persist(p);
        }
        for (Complaint c : listComplaints) {
            em.persist(c);
        }
        em.persist(commerce);
        return commerce;
    }

    @Override
    public Commerce update(Commerce commerce) {
        return em.merge(commerce);
    }

    @Override
    public Commerce updatePassword(int rut, String newPass) {
        Commerce commerceToUpdatePass = this.findByRut(rut);
        if (commerceToUpdatePass == null) return null;

        String query = "UPDATE commerce_Commerce c SET c.password = :newPass WHERE c.rut = :rut";
        Query updatePassword = em.createQuery(query);
        try {
            updatePassword.setParameter("newPass", newPass);
            updatePassword.setParameter("rut", rut);
            updatePassword.executeUpdate();
            em.flush();

            return this.findByRut(rut);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void delete(int rut) {
        em.remove(this.findByRut(rut));
    }
}
