package PaymentMethod.intefase.remote.rest.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PaymentDataDTO {
    private float amount;
    private int posId;
    private CardDataDTO cardData;
    private long commerceRut;

    public PaymentDataDTO() {}
}
