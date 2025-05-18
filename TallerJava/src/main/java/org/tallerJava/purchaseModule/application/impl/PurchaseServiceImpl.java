package org.tallerJava.purchaseModule.application.impl;

import org.tallerJava.purchaseModule.application.PurchaseService;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.tallerJava.purchaseModule.domain.Card;
import org.tallerJava.purchaseModule.domain.Purchase;

import org.tallerJava.purchaseModule.domain.PurchaseCommerce;
import org.tallerJava.purchaseModule.domain.PurchasePos;
import org.tallerJava.purchaseModule.domain.repo.CommerceRepository;
import org.tallerJava.purchaseModule.domain.repo.PosRepository;
import org.tallerJava.purchaseModule.domain.repo.PurchaseRepository;


@ApplicationScoped
public class PurchaseServiceImpl implements PurchaseService {
    @Inject
    private PurchaseRepository purchaseRepository;
    @Inject
    private CommerceRepository CommerceRepository;
    @Inject
    private PosRepository PosRepository;

    @Override
    public void processPayment(Purchase purchase, Card card,int rut,int posId){
        PurchaseCommerce commerce = CommerceRepository.findByRut(rut);
        PurchasePos pos = PosRepository.findById(posId);
        if (!pos.isStatus()) {
            throw new IllegalStateException("El POS está inactivo o no disponible");
        }
        purchase.setPos(pos);
        purchase.setCard(card);
        purchaseRepository.create(purchase);
        commerce.addPurchase(purchase);
    }
}