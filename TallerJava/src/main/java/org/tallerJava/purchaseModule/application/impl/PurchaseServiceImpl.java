package org.tallerJava.purchaseModule.application.impl;

import jakarta.transaction.Transactional;
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
import org.tallerJava.purchaseModule.interfase.remote.rest.dto.SalesSummaryDTO;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@ApplicationScoped
public class PurchaseServiceImpl implements PurchaseService {
    @Inject
    private PurchaseRepository purchaseRepository;
    @Inject
    private CommerceRepository CommerceRepository;
    @Inject
    private PosRepository PosRepository;

    @Override
    public void processPayment(Purchase purchase, Card card, int rut, int posId) {
        PurchaseCommerce commerce = CommerceRepository.findByRut(rut);
        PurchasePos pos = PosRepository.findById(posId);
        if (!pos.isStatus()) {
            throw new IllegalStateException("El POS está inactivo o no disponible");
        }
        purchase.setPos(pos);
        purchase.setCard(card);
        purchaseRepository.create(purchase);
        commerce.addPurchase(purchase);
        commerce.addPurchaseAmount(purchase.getAmount(), purchase.getDate(), new Date());
    }

    @Override
    @Transactional
    public List<Purchase> getPurchasesOfTheDay(int rut) {
        // Al estar dentro de @Transactional, no hay problemas de lazy loading
        PurchaseCommerce commerce = CommerceRepository.findByRut(rut);
        
        // Filtramos las compras del día actual
        LocalDate today = LocalDate.now();
        return commerce.getPurchases().stream()
                .filter(p -> {
                    LocalDate purchaseDate = p.getDate().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate();
                    return purchaseDate.equals(today);
                })
                .collect(Collectors.toList());
    }

    @Override
    @Transactional
    public double getTotalSalesAmount(int rut) {
        // Al estar dentro de @Transactional, no hay problemas de lazy loading
        PurchaseCommerce commerce = CommerceRepository.findByRut(rut);
        // La colección se cargará automáticamente al acceder a ella dentro de la transacción
        return commerce.getTotalSalesAmount();
    }

    @Override
    @Transactional
    public SalesSummaryDTO getSalesSummaryOfTheDay(int rut) {
        // Al estar dentro de @Transactional, no hay problemas de lazy loading
        PurchaseCommerce commerce = CommerceRepository.findByRut(rut);
        
        // Filtramos las compras del día actual
        LocalDate today = LocalDate.now();
        List<Purchase> comprasHoy = commerce.getPurchases().stream()
                .filter(p -> {
                    LocalDate purchaseDate = p.getDate().toInstant()
                            .atZone(ZoneId.systemDefault()).toLocalDate();
                    return purchaseDate.equals(today);
                })
                .collect(Collectors.toList());
        
        // Calculamos el total o utilizamos el que ya tiene la entidad
        double totalSalesAmount = commerce.getTotalSalesAmount();
        
        // Construimos el DTO con los datos obtenidos
        return SalesSummaryDTO.from(comprasHoy, totalSalesAmount);
    }
}