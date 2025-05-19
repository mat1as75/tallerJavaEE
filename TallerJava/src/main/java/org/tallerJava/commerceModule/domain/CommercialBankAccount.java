package org.tallerJava.commerceModule.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "commerce_CommercialBankAccount")
public class CommercialBankAccount {

    @Id
    private int accountNumber;

    public CommercialBankAccount(int accountNumber){
        this.accountNumber = accountNumber;
    }

    public CommercialBankAccount() {}
}
