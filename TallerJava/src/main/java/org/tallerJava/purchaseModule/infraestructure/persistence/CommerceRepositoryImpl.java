package org.tallerJava.purchaseModule.infraestructure.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.tallerJava.purchaseModule.domain.PurchaseCommerce;
import org.tallerJava.purchaseModule.domain.repo.CommerceRepository;


@ApplicationScoped
public class CommerceRepositoryImpl implements CommerceRepository {
    @PersistenceContext(unitName = "tallerjavadb")
    private EntityManager em;

    @Override
    @Transactional
    public PurchaseCommerce findByRut(long rut) {
        try {
            return em.createQuery(
                            "SELECT c FROM purchase_Commerce c WHERE c.rut = :rut",
                            PurchaseCommerce.class
                    )
                    .setParameter("rut", rut)
                    .getSingleResult();
        } catch (NoResultException e) {
            throw new NoResultException("No se encontró comercio con el RUT: " + rut);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }



}