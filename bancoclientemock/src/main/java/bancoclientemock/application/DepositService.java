package bancoclientemock.application;

import bancoclientemock.domain.DepositResponse;

public interface DepositService {
    DepositResponse makeDeposit(String commercialBankAccount, int amount);
}
