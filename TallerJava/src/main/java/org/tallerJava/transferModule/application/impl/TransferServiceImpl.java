package org.tallerJava.transferModule.application.impl;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.transaction.Transactional;
import org.jboss.logging.Logger;
import org.tallerJava.commerceModule.application.impl.CommerceServiceImpl;
import org.tallerJava.transferModule.application.TransferService;
import org.tallerJava.transferModule.application.dto.DepositDTO;
import org.tallerJava.transferModule.domain.DateRange;
import org.tallerJava.transferModule.domain.Deposit;
import org.tallerJava.transferModule.domain.repo.TransferRepository;

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


//    @Override
//    @Transactional
//    public void transferDeposit(String accountNumber, float amount) {
//
//        // Notifico transferencia a MOCK Banco Cliente (Soy Cliente SOAP)
////        try {
////
////        } catch () {
////
////        }
////        transferRepository.transferDeposit(amount, accountNumber);
//    }

    /* Devuelve los depósitos realizados para
    *  un comercio en un rango de fechas.
    * */
    @Override
    @Transactional
    public List<DepositDTO> getDepositsSummaryByPeriod(long commerceRut, DateRange range) {
        // Convertimos a LocalDateTime (por defecto 00:00:00 para el comienzo del día)
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
