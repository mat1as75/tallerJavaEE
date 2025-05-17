package org.tallerJava.commerceModule.infrastructure.persistence;

import jakarta.inject.Inject;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;
import org.tallerJava.commerceModule.domain.Pos;
import org.tallerJava.commerceModule.domain.repo.CommerceRepository;
import org.tallerJava.commerceModule.domain.repo.PosRepository;

import java.util.ArrayList;
import java.util.List;

public class PosRepositoryImpl implements PosRepository {

    @PersistenceContext
    private EntityManager em;
    private final List<Pos> posList = new ArrayList<>();

    @Inject
    private CommerceRepository commerceRepository;

    public PosRepositoryImpl() {
        posList.add(new Pos(1, true));
        posList.add(new Pos(2, true));
        posList.add(new Pos(3, false));
    }

    @Override
    public Pos findById(int id) {
        String query = "SELECT p FROM commerce_Pos p WHERE p.id = :id";
        System.out.println("RUT DE COMMERCE: " + id);
        TypedQuery<Pos> findById = em.createQuery(query, Pos.class);
        try {
            return findById.setParameter("id", id).getSingleResult();
        } catch (NoResultException e) {
            return null;
        }
    }

    @Override
    public void createPos(Commerce commerce, Pos pos) {
        Commerce commerceUpdate = commerceRepository.findByRut(commerce.getRut());
        List<Pos> listPosUpdate = commerceUpdate.getListPos();

        listPosUpdate.add(pos);
        commerceUpdate.setListPos(listPosUpdate);
    }

    @Override
    public void changePosState(Commerce commerce, Pos pos, boolean status) {
        Commerce commerceUpdate = commerceRepository.findByRut(commerce.getRut());
        Pos posUpdate = this.findById(pos.getId());
        if (commerceUpdate != null && commerceUpdate.getListPos().contains(pos)) {
            posUpdate.setStatus(status);
            commerceRepository.update(commerceUpdate);
        }
    }
}
