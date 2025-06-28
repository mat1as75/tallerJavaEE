package org.tallerJava.commerceModule.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ComplaintMessage implements Serializable {
    private static final long serialVersionUID = 1L;

    private long rutCommerce;
    private String message;
    private String timestamp;

    // Constructor con rutCommerce y message solamente
    public ComplaintMessage(long rutCommerce, String message) {
        this.rutCommerce = rutCommerce;
        this.message = message;
        this.timestamp = java.time.LocalDateTime.now().toString();
    }
}
