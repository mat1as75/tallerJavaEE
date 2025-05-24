package org.tallerJava.monitoringModule.application;

import org.tallerJava.monitoringModule.domain.Complaint;

public interface MonitoringService {
    public void notifyPayment();
    public void notifyPaymentOk();
    public void notifyPaymentFail();
    public void notifyTransfer();
    public void makeComplaint(Complaint complaint);
}
