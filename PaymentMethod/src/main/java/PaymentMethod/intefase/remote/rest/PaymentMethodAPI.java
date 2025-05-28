package PaymentMethod.intefase.remote.rest;

import PaymentMethod.intefase.remote.rest.dto.PaymentDataDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

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
    public Response processPayment(PaymentDataDTO paymentData) { //long number, String brand, String expirationDate, double amount
        if(paymentData.getAmount() > 2000){
            return Response
                    .status(Response.Status.PAYMENT_REQUIRED) // HTTP 402
                    .entity("Fondos insuficientes.")
                    .build();
        }else{
            return Response.ok(PaymentResult.OK).build();
        }
    }
}