package org.tallerJava.purchaseModule.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerJava.purchaseModule.domain.Purchase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class SalesSummaryDTO {
    private String date;
    private int transactionCount;
    private List<SingleSaleDTO> sales;

    public static SalesSummaryDTO from(List<Purchase> purchases) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dayDate = dateFormat.format(new Date());

        List<SingleSaleDTO> salesList = purchases.stream()
                .map(p -> new SingleSaleDTO(
                        p.getId(),
                        p.getAmount(),
                        dateTimeFormat.format(p.getDate()),
                        p.getDescription()))
                .collect(Collectors.toList());

        int transactionCount = salesList.size();

        return new SalesSummaryDTO(dayDate, transactionCount, salesList);
    }

}