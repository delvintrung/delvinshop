package com.example.delvin.dto.request;

import com.example.delvin.enums.PaymentProvider;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class DepositCallbackRequest {
    private String orderCode;

    private String externalTxnId;

    private boolean success;

    private BigDecimal amount;

    private String responseCode;

    private String responseMessage;

    private String rawPayload;

    private PaymentProvider paymentProvider;
}
