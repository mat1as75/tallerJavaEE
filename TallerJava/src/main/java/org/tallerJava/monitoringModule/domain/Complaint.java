package org.tallerJava.monitoringModule.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Complaint {
    int id;
    int rut_commerce;
    String message;

    public Complaint() {}
    public Complaint(int rut_commerce, String message) {
        this.rut_commerce = rut_commerce;
        this.message = message;
    }
}
