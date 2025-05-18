package org.tallerJava.purchaseModule.infraestructure.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.tallerJava.purchaseModule.domain.PurchasePos;
import org.tallerJava.purchaseModule.domain.repo.PosRepository;

@ApplicationScoped
public class PosRepositoryImpl implements PosRepository {
    @PersistenceContext(unitName = "tallerjavadb")
    private EntityManager em;

    @Override
    @Transactional
    public PurchasePos findById(int posId) {
        try {
            return em.createQuery(
                "SELECT p FROM purchase_Pos p WHERE p.id = :posId",
                    PurchasePos.class
                )
                .setParameter("posId", posId)
                .getSingleResult();
        } catch (NoResultException e) {
            throw new NoResultException("No se encontró POS con el ID: " + posId);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

}
