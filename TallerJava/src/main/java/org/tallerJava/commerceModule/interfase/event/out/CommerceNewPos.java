package org.tallerJava.commerceModule.interfase.event.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommerceNewPos {
    private long rut_commerce;
    private int id_pos;
    private boolean status;
}
