package org.tallerJava.commerceModule.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity(name = "commerce_Commerce")
@Table(name = "commerce_Commerce")
public class Commerce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long rut;
    private String email;
    private String password;
    @OneToOne(cascade = CascadeType.ALL)
    private CommercialBankAccount account;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "commerce_CommercePos",
    joinColumns = @JoinColumn(name="COMMERCE_RUT"),
    inverseJoinColumns = @JoinColumn(name="POS_ID"))
    private Set<Pos> listPos;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "commerce_CommerceComplaint",
    joinColumns = @JoinColumn(name="COMMERCE_RUT"),
    inverseJoinColumns = @JoinColumn(name="COMPLAINT_ID"))
    private Set<Complaint> listComplaints;

    public Commerce() {}

    public Commerce(long rut, String email, String password, CommercialBankAccount account, Set<Pos> listPos, Set<Complaint> listComplaints){
        this.rut = rut;
        this.email = email;
        this.password = password;
        this.account = account;
        this.listPos = listPos;
        this.listComplaints = listComplaints;
    }


}
