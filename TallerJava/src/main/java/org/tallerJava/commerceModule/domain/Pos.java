package org.tallerJava.commerceModule.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(name = "commerce_Pos")
@Table(name = "commerce_Pos")
public class Pos {
    @Id
    private int id;
    private boolean status;

    public Pos() {}

    public Pos(int id) {
        this.id = id;
    }

    public Pos(int id, boolean status) {
        this.id = id;
        this.status = status;
    }


}
