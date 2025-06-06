package org.tallerJava.commerceModule.application.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerJava.commerceModule.infrastructure.security.identitystore.Group;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupDTO {
    private String name;

    /* DTO has the responsibility of building its corresponding business object */
    public Group buildGroup() { return new Group(name); };
}
