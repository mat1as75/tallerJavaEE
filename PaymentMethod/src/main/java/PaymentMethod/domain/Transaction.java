package PaymentMethod.domain;

import PaymentMethod.intefase.remote.rest.dto.PaymentDataDTO;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
public class Transaction {
    private String id;
    private PaymentDataDTO paymentData;
    private String status;
    private LocalDateTime timestamp;

    public Transaction(PaymentDataDTO paymentData, String status) {
        this.id = java.util.UUID.randomUUID().toString();
        this.paymentData = paymentData;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }
}
