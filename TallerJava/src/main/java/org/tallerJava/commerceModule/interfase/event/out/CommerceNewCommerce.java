package org.tallerJava.commerceModule.interfase.event.out;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CommerceNewCommerce {
    //no uso VehiculoDTO para maneter bajo el acoplamiento
    //de lo contrario el modulo de Peaje que es quien va a escuchar este evento
    //va a tener una dependencia transitiva con el DTO
    private int rut;
    private String email;
    private String password;
    private CommerceCommercialBankAccount account;
    private List<CommercePos> listPos;
    private List<CommerceComplaint> listComplaint;
}
