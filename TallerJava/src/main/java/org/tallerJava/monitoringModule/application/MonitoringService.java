package org.tallerJava.monitoringModule.application;

import org.tallerJava.monitoringModule.domain.Complaint;
import org.tallerJava.monitoringModule.domain.Payment;

public interface MonitoringService {
    public void notifyPayment(Payment payment);
    public void notifyPaymentOk(Payment payment);
    public void notifyPaymentFail(Payment payment);
    public void notifyTransfer();
    public void makeComplaint(Complaint complaint);
}
