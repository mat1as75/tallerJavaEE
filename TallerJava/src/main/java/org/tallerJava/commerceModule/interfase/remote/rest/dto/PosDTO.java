package org.tallerJava.commerceModule.interfase.remote.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.Pos;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PosDTO {
    private int id;
    private boolean status;

    /* DTO has the responsibility of building its corresponding business object */
    public Pos buildPos() {
        return new Pos(id, status);
    }
}
