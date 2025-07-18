package org.tallerJava.commerceModule.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "commerce_CommercialBankAccount")
@Table(name = "commerce_CommercialBankAccount")
public class  CommercialBankAccount {

    @Id
    private String accountNumber;

    public CommercialBankAccount(String accountNumber){
        this.accountNumber = accountNumber;
    }

    public CommercialBankAccount() {}
}
