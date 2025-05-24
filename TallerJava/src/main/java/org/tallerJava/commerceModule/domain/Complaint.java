package org.tallerJava.commerceModule.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "commerce_Complaint")
@Table(name = "commerce_Complaint")
public class Complaint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;
    String message;

    public Complaint() {}

    public Complaint(String message) { this.message = message; }

    public Complaint(int id, String message) {
        this.id = id;
        this.message = message;
    }
}
