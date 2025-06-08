package org.tallerJava.commerceModule.interfase.event.out;

import lombok.AllArgsConstructor;
import lombok.Data;
import java.util.Map;

@Data
@AllArgsConstructor
public class CommerceNewCommerce {
    //no uso DTOs para mantener bajo el acoplamiento
    //de lo contrario el modulo de Monitoreo que es quien va a escuchar este evento
    //va a tener dependencias transitivas con los DTOs
    private long rut;
    private String email;
    private String password;
    private String accountNumber;
    private Map<Integer, Boolean> mapPos;
    private Map<Integer, String> mapComplaint;
}
