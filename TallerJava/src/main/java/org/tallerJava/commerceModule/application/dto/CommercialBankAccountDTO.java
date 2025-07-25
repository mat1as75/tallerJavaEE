package org.tallerJava.commerceModule.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommercialBankAccountDTO {
    private String accountNumber;

    /* DTO has the responsibility of building its corresponding business object */
    public CommercialBankAccount buildCommercialBankAccount() {
        return new CommercialBankAccount(accountNumber);
    }
}