package PaymentMethod.intefase.remote.rest;

import PaymentMethod.domain.Transaction;
import PaymentMethod.intefase.remote.rest.dto.PaymentDataDTO;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ApplicationScoped
@Path("/api")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class PaymentMethodAPI {

    // Lista para almacenar transacciones en memoria
    private final List<Transaction> pendingTransactions = Collections.synchronizedList(new ArrayList<>());

    public enum PaymentResult {
        OK, INSUFFICIENT_FUNDS, ERROR
    }

    @POST
    @Transactional
    public Response processPayment(PaymentDataDTO paymentData) { //long number, String brand, String expirationDate, double amount
        if(paymentData.getAmount() > 2000){
            // Registrar transacción rechazada
            Transaction transaction = new Transaction(paymentData, PaymentResult.INSUFFICIENT_FUNDS.name());
            pendingTransactions.add(transaction);

            return Response
                    .status(Response.Status.PAYMENT_REQUIRED) // HTTP 402
                    .entity("Fondos insuficientes.")
                    .build();
        }else{
            // Registrar transacción exitosa
            Transaction transaction = new Transaction(paymentData, PaymentResult.OK.name());
            pendingTransactions.add(transaction);

            return Response.ok(PaymentResult.OK).build();
        }
    }

    public List<Transaction> getPendingTransactions() {
        return Collections.unmodifiableList(pendingTransactions);
    }

    public boolean confirmTransaction(String transactionId) {
        //Esto se debe hacer cuando enviamos la notificacion al endpoint de transferencia
        return pendingTransactions.removeIf(t -> t.getId().equals(transactionId));
    }

    public void confirmAllTransactions() {
        pendingTransactions.clear();
    }
}