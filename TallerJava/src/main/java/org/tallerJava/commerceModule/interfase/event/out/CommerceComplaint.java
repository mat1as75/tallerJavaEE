package org.tallerJava.commerceModule.interfase.event.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommerceComplaint {
    private int id;
    private String message;
}
