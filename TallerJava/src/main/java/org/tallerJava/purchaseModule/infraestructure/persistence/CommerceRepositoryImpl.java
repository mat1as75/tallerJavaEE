package org.tallerJava.purchaseModule.infraestructure.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.tallerJava.purchaseModule.domain.PurchaseCommerce;
import org.tallerJava.purchaseModule.domain.repo.CommerceRepository;

import java.util.List;


@ApplicationScoped
public class CommerceRepositoryImpl implements CommerceRepository {
    @PersistenceContext(unitName = "tallerjavadb")
    private EntityManager em;

    @Override
    @Transactional
    public PurchaseCommerce findByRut(long rut) {
        try {
            List<PurchaseCommerce> commerce = em.createQuery(
                            "SELECT c FROM purchase_Commerce c WHERE c.rut = :rut",
                            PurchaseCommerce.class
                    )
                    .setParameter("rut", rut)
                    .getResultList();

            return commerce.isEmpty() ? null : commerce.getFirst();
        } catch (NoResultException e) {
            throw new NoResultException("No se encontró comercio con el RUT: " + rut);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public boolean create(PurchaseCommerce commerce) {
        if (commerce.getRut() != 0)
            if (this.findByRut(commerce.getRut()) != null) return false;

        try {
            em.persist(commerce);
            return true;
        } catch (PersistenceException e) {
            return false;
        }
    }

}