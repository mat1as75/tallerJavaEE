package org.tallerJava.commerceModule.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "commerce_Complaint")
@Table(name = "commerce_Complaint")
public class Complaint {
    @Id
    int id;
    String message;

    public Complaint() {}

    public Complaint(int id) { this.id = id; }

    public Complaint(int id, String message) {
        this.id = id;
        this.message = message;
    }
}
