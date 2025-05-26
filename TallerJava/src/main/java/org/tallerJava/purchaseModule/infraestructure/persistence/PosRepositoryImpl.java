package org.tallerJava.purchaseModule.infraestructure.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.infrastructure.persistence.CommerceRepositoryImpl;
import org.tallerJava.purchaseModule.domain.PurchaseCommerce;
import org.tallerJava.purchaseModule.domain.PurchasePos;
import org.tallerJava.purchaseModule.domain.repo.CommerceRepository;
import org.tallerJava.purchaseModule.domain.repo.PosRepository;

import java.util.List;

@ApplicationScoped
public class PosRepositoryImpl implements PosRepository {
    private static final Logger log = Logger.getLogger(CommerceRepositoryImpl.class);

    @PersistenceContext(unitName = "tallerjavadb")
    private EntityManager em;

    @Inject
    private CommerceRepository commerceRepository;

    @Override
    @Transactional
    public PurchasePos findById(int posId) {
        try {
            String query = "SELECT p FROM purchase_Pos p WHERE p.id = :posId";

            List<PurchasePos> pos = em.createQuery(query, PurchasePos.class)
                    .setParameter("posId", posId)
                    .getResultList();

            return pos.isEmpty() ? null : pos.getFirst();
        } catch (NoResultException e) {
            log.error("No se encontró pos con id: " + posId);
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    @Transactional
    public boolean createPos(PurchasePos pos) {
        try {
            em.merge(pos);
            return true;
        } catch (PersistenceException e) {
            return false;
        }
    }

    @Override
    @Transactional
    public int changePosStatus(PurchasePos pos) {
        PurchasePos posToUpdate = this.findById(pos.getId());
        if (posToUpdate == null) return -1;

        try {
            posToUpdate.setStatus(pos.isStatus());
            em.merge(posToUpdate);
            return 1;
        } catch (PersistenceException e) {
            return 0;
        }
    }

}
