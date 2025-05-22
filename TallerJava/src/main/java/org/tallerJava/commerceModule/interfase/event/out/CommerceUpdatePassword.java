package org.tallerJava.commerceModule.interfase.event.out;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CommerceUpdatePassword {
    private long rut;
    private String newPassword;
}
