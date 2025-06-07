package org.tallerJava.transferModule.application;

import org.tallerJava.transferModule.application.dto.DepositDTO;
import org.tallerJava.transferModule.domain.DateRange;
import java.util.List;

public interface TransferService {
    //public void transferDeposit(float amount, int accountNumber);

    public List<DepositDTO> getDepositsSummaryByPeriod(long commerceRut, DateRange range);
}
