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

    @Enumerated(EnumType.STRING)
    @Column(name = "qualification")
    private Qualification qualification;


    public Complaint() {}

    public Complaint(String message) { this.message = message; }

    public Complaint(int id, String message) { //Verificar si se utiliza esto
        this.id = id;
        this.message = message;
    }

    public Complaint(String message, Qualification qualification) {
        this.message = message;
        this.qualification = qualification;
    }

}
