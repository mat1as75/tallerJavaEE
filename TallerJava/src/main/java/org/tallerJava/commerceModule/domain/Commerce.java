package org.tallerJava.commerceModule.domain;

import org.tallerJava.commerceModule.domain.CommercialBankAccount;

public class Commerce {

    private int rut;
    private String email;
    private String password;
    private CommercialBankAccount account;

    public Commerce(int rut, String email, String password, CommercialBankAccount account){
        this.rut = rut;
        this.email = email;
        this.password = password;
        this.account = account;
    }

    public int getRut() {
        return rut;
    }

    public void setRut(int rut) {
        this.rut = rut;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public CommercialBankAccount getAccount() {
        return account;
    }

    public void setAccount(CommercialBankAccount account) {
        this.account = account;
    }
}
