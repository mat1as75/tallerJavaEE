package org.tallerJava.commerceModule.infrastructure.security.identitystore;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "`Group`")
public class Group {
    @Id
    private String name;

    public Group() {}

    public Group(String name) { this.name = name; }

}
