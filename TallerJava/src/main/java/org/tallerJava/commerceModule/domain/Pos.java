package org.tallerJava.commerceModule.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "commerce_Pos")
@Table(name = "commerce_Pos")
public class Pos {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    //private int importAmount;
    //private datosTarjeta

    private boolean status;

    public Pos() {}

    public Pos(int id) {
        this.id = id;
    }

    public Pos(boolean status) {
        this.status = status;
    }

    public Pos(int id, boolean status) {
        this.id = id;
        this.status = status;
    }


}
