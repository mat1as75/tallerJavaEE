package PaymentMethod.intefase.remote.rest;

import PaymentMethod.intefase.remote.rest.dto.PaymentDataDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@ApplicationScoped
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentMethodAPI {

    public enum PaymentResult {
        OK, INSUFFICIENT_FUNDS, ERROR
    }

    @POST
    @Transactional
    public PaymentResult processPayment(PaymentDataDTO paymentData) { //long number, String brand, String expirationDate, double amount
        return PaymentResult.OK;
    }
}