package org.tallerJava.monitoringModule.application.impl;

import jakarta.enterprise.context.ApplicationScoped;
import org.tallerJava.monitoringModule.application.MonitoringService;
import org.tallerJava.monitoringModule.domain.Complaint;
import org.tallerJava.monitoringModule.domain.Payment;

@ApplicationScoped
public class MonitoringServiceImpl implements MonitoringService {

    @Override
    public void notifyPayment(Payment payment) {
        System.out.println("Evento Pago Recibido");
    }

    @Override
    public void notifyPaymentOk(Payment payment) {
        System.out.println("Evento Pago Aprobado Recibido");
    }

    @Override
    public void notifyPaymentFail(Payment payment) {
        System.out.println("Evento Pago Fallido Recibido");
    }

    @Override
    public void notifyTransfer() {
        System.out.println("Evento Transferencia Recibido");
    }

    @Override
    public void makeComplaint(Complaint complaint) {
        System.out.println("Evento Reclamo Recibido: " + complaint.getMessage());
    }
}
