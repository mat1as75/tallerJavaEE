package bancoclientemock.application.impl;

import bancoclientemock.application.DepositService;
import bancoclientemock.domain.Deposit;
import bancoclientemock.domain.DepositResponse;
import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@ApplicationScoped
public class DepositServiceImpl implements DepositService {

    private List<Deposit> deposits;

    @PostConstruct
    private void initialize() {
        System.out.println("Inicializando depósitos");
        this.deposits = new ArrayList<>();
        deposits.add(new Deposit("ACC-1001", 1000));
        deposits.add(new Deposit("ACC-2002", 2000));
    }

    @Override
    public DepositResponse makeDeposit(String commercialBankAccount, int amount) {
            int id = deposits.size() + 1;
            deposits.add(new Deposit(commercialBankAccount, amount));
            return new DepositResponse(true, "Depósito confirmado", id);
    }
}
