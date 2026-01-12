package com.example.delvin.dto.request;

import com.example.delvin.enums.PaymentProvider;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DepositTransactionInsertRequest {
    private Long userId;
    private BigDecimal amount;
    private String currency;
    private PaymentProvider paymentProvider;
}
