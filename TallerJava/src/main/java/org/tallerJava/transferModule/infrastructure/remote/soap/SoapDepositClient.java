package org.tallerJava.transferModule.infrastructure.remote.soap;

import org.tallerJava.transferModule.infrastructure.remote.soap.generated.DepositResponse;
import org.tallerJava.transferModule.infrastructure.remote.soap.generated.DepositSOAP;
import org.tallerJava.transferModule.infrastructure.remote.soap.generated.DepositSOAPService;

public class SoapDepositClient {
    private final DepositSOAP depositSOAP;

    public SoapDepositClient() {
        // Create the client
        DepositSOAPService service = new DepositSOAPService();
        this.depositSOAP = service.getDepositSOAPPort();
    }

    public DepositResponse makeDeposit(String accountNumber, int amount) {
        return depositSOAP.makeDeposit(accountNumber, amount);
    }
}
