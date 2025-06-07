package org.tallerJava.transferModule.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Entity(name = "transfer_Deposit")
@Table(name = "transfer_Deposit")
public class Deposit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    private long commerceRut;
    private float amount;
    private int accountNumber;
    @Column(name = "creation_date")
    private LocalDateTime creationDate;

    public Deposit() {}

    public Deposit(long commerceRut, float amount, int accountNumber, LocalDateTime creationDate) {
        this.commerceRut = commerceRut;
        this.amount = amount;
        this.accountNumber = accountNumber;
        this.creationDate = creationDate;
    }
}
