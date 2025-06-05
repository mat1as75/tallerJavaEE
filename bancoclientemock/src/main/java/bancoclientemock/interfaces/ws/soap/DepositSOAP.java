package bancoclientemock.interfaces.ws.soap;

import bancoclientemock.application.DepositService;
import bancoclientemock.domain.DepositResponse;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.jws.WebMethod;
import jakarta.jws.WebParam;
import jakarta.jws.WebResult;
import jakarta.jws.WebService;
import jakarta.jws.soap.SOAPBinding;

@WebService
@SOAPBinding(style = SOAPBinding.Style.RPC)
@ApplicationScoped
public class DepositSOAP {

    @Inject
    private DepositService depositService;

    @WebMethod
    @WebResult(name = "DepositResponse")
    public DepositResponse makeDeposit(
            @WebParam(name = "commercialBankAccount") String commercialBankAccount,
            @WebParam(name = "amount") int amount) {
        return depositService.makeDeposit(commercialBankAccount, amount);
    }
}
