package org.tallerJava.commerceModule.application.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.tallerJava.commerceModule.domain.Commerce;
import org.tallerJava.commerceModule.domain.CommercialBankAccount;
import org.tallerJava.commerceModule.domain.Complaint;
import org.tallerJava.commerceModule.domain.Pos;

import java.util.Set;
import java.util.stream.Collectors;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommerceDTO {
    private long rut;
    private String email;
    private String password;
    @JsonProperty("account")
    private CommercialBankAccountDTO account;
    @JsonProperty("listPos")
    private Set<PosDTO> listPos;
    @JsonProperty("listComplaints")
    private Set<ComplaintDTO> listComplaint;

    /* DTO has the responsibility of building its corresponding business object */
    public Commerce buildCommerce() {
        CommercialBankAccount account = null;
        Set<Pos> listPos = null;
        Set<Complaint> listComplaint = null;

        if (this.account != null) {
            account = this.account.buildCommercialBankAccount();
        }

        if (this.listPos != null) {
            listPos = this.listPos.stream()
                    .map(PosDTO::buildPos)
                    .collect(Collectors.toSet());
        }

        if (this.listComplaint != null) {
            listComplaint = this.listComplaint.stream()
                    .map(ComplaintDTO::buildComplaint)
                    .collect(Collectors.toSet());
        }

        return new Commerce(rut, email, password, account, listPos, listComplaint);
    }
}
