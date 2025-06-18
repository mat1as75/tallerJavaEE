package org.tallerJava.monitoringModule.application;

import org.tallerJava.monitoringModule.domain.Complaint;
import org.tallerJava.monitoringModule.domain.Deposit;
import org.tallerJava.monitoringModule.domain.Payment;
import org.tallerJava.monitoringModule.domain.SalesReport;

public interface MonitoringService {
    //public void notifyPayment(Payment payment); // -> NO ES NECESARIO
    public void notifyPaymentOk(Payment payment);
    public void notifyPaymentFail(Payment payment);
    public void notifySalesReport(SalesReport salesReport); // -> CANTIDAD DE REPORTES DE VENTAS REALIZADAS
    public void notifyTransfer(Deposit deposit);
    public void makeComplaint(Complaint complaint);
}
