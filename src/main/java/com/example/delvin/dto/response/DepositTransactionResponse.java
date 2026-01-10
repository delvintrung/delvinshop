package com.example.delvin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class DepositTransactionResponse {
    private Long id;
    private Double amount;
    private String bankAccount;
    private String bankTransactionId;
    private String description;
    private Instant processedAt;
    private Instant transactionAt;
    private String status;
}
