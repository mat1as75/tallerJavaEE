package org.tallerJava.commerceModule.interfase.event.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommerceMakeComplaint {
    private long rut_commerce;
    private String message;
}
