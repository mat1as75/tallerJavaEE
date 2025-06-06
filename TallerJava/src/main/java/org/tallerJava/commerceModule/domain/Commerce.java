package org.tallerJava.commerceModule.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;
import org.tallerJava.commerceModule.infrastructure.security.HashFunctionUtil;
import org.tallerJava.commerceModule.infrastructure.security.identitystore.Group;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@Entity(name = "commerce_Commerce")
@Table(name = "commerce_Commerce")
public class Commerce {

    @Id
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "commerce_Commerce_Group",
    joinColumns = @JoinColumn(name = "commerce_rut"),
    inverseJoinColumns = @JoinColumn(name = "group_name"))
    private Set<Group> listGroups;

    public Commerce() {}

    public Commerce(long rut, String email, String password, CommercialBankAccount account, Set<Pos> listPos, Set<Complaint> listComplaints, Set<Group> listGroup){
        this.rut = rut;
        this.email = email;
        this.password = password;
        this.account = account;
        this.listPos = listPos;
        this.listComplaints = listComplaints;
        this.listGroups = listGroup;
    }

    public List<String> groupsAsString() {
        List<String> groupList = new ArrayList<>();
        if (this.listGroups != null) {
            for (Group g : this.listGroups) {
                groupList.add(g.getName());
            }
        }

        return groupList;
    }

    public boolean correctPassword(String password) {
        return HashFunctionUtil.convertToHas(password).equals(this.password);
    }


}
