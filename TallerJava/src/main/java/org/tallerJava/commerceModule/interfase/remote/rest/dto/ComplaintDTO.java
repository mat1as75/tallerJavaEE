package org.tallerJava.commerceModule.interfase.remote.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerJava.commerceModule.domain.Complaint;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComplaintDTO {
    private int id;
    private String message;

    /* DTO has the responsibility of building its corresponding business object */
    public Complaint buildComplaint() {
        return new Complaint(message);
    }
}
