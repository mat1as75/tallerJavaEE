package org.tallerJava.monitoringModule.domain;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SalesReport {
    private long rut_commerce;

    public SalesReport() {}
    public SalesReport(long rut_commerce) {
        this.rut_commerce = rut_commerce;
    }
}
