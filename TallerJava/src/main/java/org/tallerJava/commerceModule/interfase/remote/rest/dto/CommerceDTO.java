package org.tallerJava.commerceModule.interfase.remote.rest.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.Complaint;
import org.tallerJava.commerceModule.domain.Pos;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommerceDTO {
    private int rut;
    private String email;
    private String password;
    private CommercialBankAccountDTO account;
    private Set<PosDTO> listPos;
    @JsonProperty("listComplaints")
    private Set<ComplaintDTO> listComplaint;

    /* DTO has the responsibility of building its corresponding business object */
    public Commerce buildCommerce() {
        Set<Pos> listPos = this.listPos.stream()
                .map(PosDTO::buildPos)
                .collect(Collectors.toSet());

        Set<Complaint> listComplaint = this.listComplaint.stream()
                .map(ComplaintDTO::buildComplaint)
                .collect(Collectors.toSet());

        return new Commerce(rut, email, password, account.buildCommercialBankAccount(), listPos, listComplaint);
    }
}
