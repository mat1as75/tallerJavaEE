package org.tallerJava.commerceModule.domain;

public class CommercialBankAccount {

    private int accountNumber;

    public CommercialBankAccount(int accountNumber){
        this.accountNumber = accountNumber;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public void setAccountNumber(int accountNumber) {
        this.accountNumber = accountNumber;
    }
}
