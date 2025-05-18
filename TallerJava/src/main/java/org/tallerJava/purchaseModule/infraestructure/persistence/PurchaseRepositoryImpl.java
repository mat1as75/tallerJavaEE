package org.tallerJava.purchaseModule.infraestructure.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import org.tallerJava.purchaseModule.domain.Purchase;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.tallerJava.purchaseModule.domain.repo.PurchaseRepository;

@ApplicationScoped
public class PurchaseRepositoryImpl implements PurchaseRepository {
    @PersistenceContext(unitName = "tallerjavadb")
    private EntityManager em;

    @Override
    @Transactional
    public void create(Purchase purchase) {
        try {
            em.persist(purchase);
        }
        catch(Exception e){
            throw new RuntimeException(e);
        }
    }
}