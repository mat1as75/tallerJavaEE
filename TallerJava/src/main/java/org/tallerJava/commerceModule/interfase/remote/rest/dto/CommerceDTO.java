package org.tallerJava.commerceModule.interfase.remote.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerJava.commerceModule.domain.Commerce;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommerceDTO {
    private int rut;
    private String email;
    private String password;
    private CommercialBankAccountDTO account;

    /* DTO has the responsibility of building its corresponding business object */
    public Commerce buildCommerce() {
        return new Commerce(rut, email, password, account.buildCommercialBankAccount());
    }
}
