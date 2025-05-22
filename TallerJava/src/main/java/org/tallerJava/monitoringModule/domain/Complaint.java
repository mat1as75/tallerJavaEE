package org.tallerJava.monitoringModule.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Complaint {
    int id;
    long rut_commerce;
    String message;

    public Complaint() {}
    public Complaint(long rut_commerce, String message) {
        this.rut_commerce = rut_commerce;
        this.message = message;
    }
}
