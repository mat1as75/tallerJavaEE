package org.tallerJava.commerceModule.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Entity(name = "commerce_Commerce")
@Table(name = "commerce_Commerce")
public class Commerce {

    @Id
    private int rut;
    private String email;
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    private CommercialBankAccount account;

    @OneToMany
    @JoinTable(name = "commerce_CommercePos",
    joinColumns = @JoinColumn(name="COMMERCE_RUT"),
    inverseJoinColumns = @JoinColumn(name="POS_ID"))
    private List<Pos> listPos = new ArrayList<>();

    @OneToMany
    @JoinTable(name = "commerce_CommerceComplaint",
    joinColumns = @JoinColumn(name="COMMERCE_RUT"),
    inverseJoinColumns = @JoinColumn(name="COMPLAINT_ID"))
    private List<Complaint> listComplaints = new ArrayList<>();

    public Commerce() {}

    public Commerce(int rut, String email, String password, CommercialBankAccount account, List<Pos> listPos, List<Complaint> listComplaints){
        this.rut = rut;
        this.email = email;
        this.password = password;
        this.account = account;
        this.listPos = listPos;
        this.listComplaints = listComplaints;
    }


}
