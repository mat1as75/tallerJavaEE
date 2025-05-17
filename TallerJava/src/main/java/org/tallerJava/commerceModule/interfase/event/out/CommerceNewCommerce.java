package org.tallerJava.commerceModule.interfase.event.out;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommerceNewCommerce {
    private int rut;
    private String email;
    private String password;
    private CommerceCommercialBankAccount account;
    private List<CommercePos> listPos;
    private List<CommerceComplaint> listComplaint;
}
