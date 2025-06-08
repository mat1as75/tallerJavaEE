package org.tallerJava.transferModule.infrastructure.persistence;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.*;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;
import org.tallerJava.transferModule.domain.Deposit;
import org.tallerJava.transferModule.domain.repo.TransferRepository;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
public class TransferRepositoryImpl implements TransferRepository {
    private static final Logger log = Logger.getLogger(TransferRepositoryImpl.class);

    @PersistenceContext
    private EntityManager em;

    @Override
    @Transactional
    public List<Deposit> findByPeriod(long commerceRut, LocalDateTime start, LocalDateTime end) {
        try {
            TypedQuery<Deposit> query = em.createQuery("SELECT d FROM transfer_Deposit d WHERE d.commerceRut = :commerceRut AND d.creationDate >= :start AND d.creationDate < :end", Deposit.class);
            query.setParameter("commerceRut", commerceRut);
            query.setParameter("start", start);
            query.setParameter("end", end);
            return query.getResultList();
        } catch (NoResultException e) {
            log.warn("No se encontraron depósitos para el comercio dado y rango de fechas: " + commerceRut + " - " + start + " - " + end);
            return Collections.emptyList();
        } catch (Exception e) {
            log.error("Error inesperado buscando depósitos");
            throw new RuntimeException("Error buscando depósitos para el comercio dado y rango de fechas", e);
        }
    }

    @Override
    @Transactional
    public void createDeposit(Deposit deposit) {
        try {
            em.persist(deposit);
        } catch (PersistenceException e) {
            throw e;
        }
    }
}
