package org.tallerJava.purchaseModule.application.dto;

import lombok.Getter;
import lombok.Setter;
import org.tallerJava.purchaseModule.domain.Purchase;
import org.tallerJava.purchaseModule.domain.Card;

import java.util.Date;


@Getter
@Setter
public class PaymentDataDTO {
    private float amount;
    private int posId;
    private CardDataDTO cardData;
    private long commerceRut;

    public static Purchase buildPurchase(PaymentDataDTO dto_purchase) {
        Purchase purchase = new Purchase();
        purchase.setAmount(dto_purchase.getAmount());
        purchase.setDate(new java.util.Date());
        purchase.setDescription("Compra realizada desde POS " + dto_purchase.getPosId());
        return purchase;
    }

    public static Card buildCard(CardDataDTO dto_card){
        Card card = new Card();
        card.setNumber(dto_card.getNumber());
        card.setBrand(dto_card.getBrand());
        Date date = null;
        try {
            String dateStr = dto_card.getDate(); // "12/11"
            String[] parts = dateStr.split("/");
            int month = Integer.parseInt(parts[0]);
            int year = 2000 + Integer.parseInt(parts[1]);

            card.setExpirationDate(String.format("%02d/%04d", month, year));

        } catch (Exception e) {
            throw new IllegalArgumentException("Formato de fecha inválido. Se esperaba MM/yy.", e);
        }
        return card;
    }
}
