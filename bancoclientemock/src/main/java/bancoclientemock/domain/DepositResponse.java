package bancoclientemock.domain;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import jakarta.xml.bind.annotation.XmlType;

@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DepositResponse", propOrder = { "success", "message", "depositId" })
@XmlRootElement(name = "DepositResponse")
public class DepositResponse {

    @XmlElement(required = true)
    private boolean success;

    @XmlElement(required = true)
    private String message;

    private Integer depositId;

    public DepositResponse() {
    }

    public DepositResponse(boolean success, String message, Integer depositId) {
        this.success = success;
        this.message = message;
        this.depositId = depositId;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getDepositId() {
        return depositId;
    }

    public void setDepositId(Integer depositId) {
        this.depositId = depositId;
    }
}
