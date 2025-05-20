package org.tallerJava.purchaseModule.infraestructure.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import org.tallerJava.purchaseModule.domain.Purchase;

import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.tallerJava.purchaseModule.domain.PurchaseCommerce;
import org.tallerJava.purchaseModule.domain.repo.PurchaseRepository;

import java.util.Date;
import java.util.List;

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

    @Override
    @Transactional
    public List<Purchase> findPurchasesByCommerceRutAndDateBetween(PurchaseCommerce commerce, Date start, Date end){
        try {
            return em.createQuery(
                            "SELECT p FROM Purchase p WHERE p.commerce = :commerce AND p.date >= :start AND p.date < :end",
                            Purchase.class
                    )
                    .setParameter("commerce", commerce)
                    .setParameter("start", start)
                    .setParameter("end", end)
                    .getResultList();
        } catch (Exception e) {
            throw new RuntimeException("Error buscando compras para el comercio dado y rango de fechas", e);
        }
    }
}