package org.tallerJava.transferModule.application.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;
import org.tallerJava.transferModule.domain.Deposit;

import java.time.LocalDateTime;
import java.util.Date;
import java.time.LocalDate;

@Getter
@Setter
public class DepositDTO {
    private long id;
    private long commerceRut;
    private float amount;
    private int accountNumber;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    public DepositDTO(long id, long commerceRut, float amount, int accountNumber, LocalDateTime creationDate) {
        this.id = id;
        this.commerceRut = commerceRut;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.creationDate = creationDate;
    }

    /* DTO has the responsibility of building its corresponding business object */
    public Deposit buildDeposit() {
        return new Deposit(commerceRut, amount, accountNumber, creationDate);
    }
}
