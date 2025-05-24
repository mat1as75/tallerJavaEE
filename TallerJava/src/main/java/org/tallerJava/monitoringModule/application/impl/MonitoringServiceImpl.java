package org.tallerJava.monitoringModule.application.impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.tallerJava.monitoringModule.application.MonitoringService;
import org.tallerJava.monitoringModule.domain.Complaint;

@ApplicationScoped
public class MonitoringServiceImpl implements MonitoringService {

    @Override
    public void notifyPayment() {}

    @Override
    public void notifyPaymentOk() {}

    @Override
    public void notifyPaymentFail() {}

    @Override
    public void notifyTransfer() {}

    @Override
    public void makeComplaint(Complaint complaint) {
        System.out.println("Evento Reclamo Recibido: " + complaint.getMessage());
    }
}
