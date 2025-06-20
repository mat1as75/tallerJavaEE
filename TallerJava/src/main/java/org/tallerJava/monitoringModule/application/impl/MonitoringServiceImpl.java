package org.tallerJava.monitoringModule.application.impl;

import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import org.tallerJava.monitoringModule.application.MonitoringService;
import org.tallerJava.monitoringModule.domain.Complaint;
import org.tallerJava.monitoringModule.domain.Deposit;
import org.tallerJava.monitoringModule.domain.Payment;
import org.tallerJava.monitoringModule.domain.SalesReport;
import org.tallerJava.monitoringModule.infrastructure.MetricsRegister;

@RequestScoped
public class MonitoringServiceImpl implements MonitoringService {
    @Inject
    private MetricsRegister metricsRegister;

    @Override
    public void notifyPaymentOk(Payment payment) {
        try {
            metricsRegister.counterIncrement(MetricsRegister.METRIC_PAYMENT_OK);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void notifyPaymentFail(Payment payment) {
        try {
            metricsRegister.counterIncrement(MetricsRegister.METRIC_PAYMENT_FAIL);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void notifySalesReport(SalesReport salesReport) {
        try {
            metricsRegister.counterIncrement(MetricsRegister.METRIC_SALES_REPORT);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void notifyTransfer(Deposit deposit) {
        try {
            metricsRegister.counterIncrement(MetricsRegister.METRIC_DEPOSIT);
        } catch (Exception e) {
            throw e;
        }
    }

    @Override
    public void makeComplaint(Complaint complaint) {
        try {
            metricsRegister.counterIncrement(MetricsRegister.METRIC_COMPLAINT);
        } catch (Exception e) {
            throw e;
        }
    }
}
