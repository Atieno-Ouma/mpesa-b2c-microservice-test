package org.example.backendtest.Entity;

import jakarta.persistence.*;
import lombok.Data;
@Entity
@Data
@Table(name = "transactions")
public class TransactionEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String transactionId;
    private double amount;
    private String status;
    private String description;
}
