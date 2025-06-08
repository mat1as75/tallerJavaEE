package org.tallerJava.transferModule.application.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.persistence.PersistenceException;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.application.impl.CommerceServiceImpl;
import org.tallerJava.transferModule.application.TransferService;
import org.tallerJava.transferModule.application.dto.DepositDTO;
import org.tallerJava.transferModule.domain.DateRange;
import org.tallerJava.transferModule.domain.Deposit;
import org.tallerJava.transferModule.domain.repo.TransferRepository;
import org.tallerJava.transferModule.infrastructure.remote.soap.SoapDepositClient;
import org.tallerJava.transferModule.infrastructure.remote.soap.generated.DepositResponse;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class TransferServiceImpl implements TransferService {
    private static final Logger log = Logger.getLogger(CommerceServiceImpl.class);

    @Inject
    private TransferRepository transferRepository;

    private static final int PROFIT_PERCENTAGE = 10;

    // Atributo para calcular beneficio del Sistema
    private float profitAmount = 0;

    @Override
    @Transactional
    public void transferDeposit(long commerceRut, String accountNumber, float amount) {
        // Descontado del importe la comisión del Sistema
        this.profitAmount += (amount * PROFIT_PERCENTAGE) / 100;
        amount = amount - (amount * PROFIT_PERCENTAGE) / 100;

        // Notifico transferencia a MOCK Banco Cliente (Soy Cliente SOAP)
        this.makeDeposit(commerceRut, accountNumber, amount);

        LocalDateTime now = LocalDateTime.now();
        Deposit deposit = new Deposit(commerceRut, amount, accountNumber, now);

        // Persisto depósito en base de datos
        try {
            transferRepository.createDeposit(deposit);
            log.infof("Actualizando beneficio del Sistema: %s", this.profitAmount);
        } catch (PersistenceException e) {
            throw e;
        }

    }

    private void makeDeposit(long commerceRut, String accountNumber, float amount) {
        log.infof("Realizando transferencia a cuenta %s en comercio %d", accountNumber, commerceRut);

        SoapDepositClient soapDepositClient = new SoapDepositClient();

        try {
            DepositResponse res = soapDepositClient.makeDeposit(accountNumber, (int) amount);
            log.infof("[SOAP RESPONSE] STATUS - " + res.isSuccess() + " | ID - " + res.getDepositId() + " | MSG - " + res.getMessage() );
        } catch (Exception e) {
            log.errorf("Error al comunicarse con el Banco Cliente para realizar la transferencia: %s %d ", accountNumber, commerceRut);
        }
    }

    @Override
    @Transactional
    public List<DepositDTO> getDepositsSummaryByPeriod(long commerceRut, DateRange range) {
        // Convierte a LocalDateTime (por defecto 00:00:00 para el comienzo del día)
        LocalDateTime startDateTime = range.getStart().atStartOfDay();
        LocalDateTime endDateTime = range.getEnd().plusDays(1).atStartOfDay().minusNanos(1);

        List<Deposit> deposits = transferRepository.findByPeriod(commerceRut, startDateTime, endDateTime);

        return deposits.stream()
                .map(deposit -> new DepositDTO(
                        deposit.getId(),
                        deposit.getCommerceRut(),
                        deposit.getAmount(),
                        deposit.getAccountNumber(),
                        deposit.getCreationDate()))
                .toList();
    }
}
