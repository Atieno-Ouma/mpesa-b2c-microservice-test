package org.example.backendtest.DTO;
import lombok.Data;

@Data
public class MpesaB2CTransactionRequest {
    private String phoneNumber;
    private double amount;
    private String transactionId;
}
