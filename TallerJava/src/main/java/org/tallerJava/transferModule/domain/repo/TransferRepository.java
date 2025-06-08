package org.tallerJava.transferModule.domain.repo;

import org.tallerJava.transferModule.domain.Deposit;

import java.time.LocalDateTime;
import java.util.List;

public interface TransferRepository {
    public List<Deposit> findByPeriod(long commerceRut, LocalDateTime start, LocalDateTime end);
    public void createDeposit(Deposit deposit);
}
