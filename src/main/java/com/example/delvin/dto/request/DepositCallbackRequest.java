package com.example.delvin.dto.request;

import com.example.delvin.enums.PaymentProvider;
import lombok.Data;

@Data
public class DepositCallbackRequest {
    private String orderCode;

    private String externalTxnId;

    private boolean success;

    private String responseCode;

    private String responseMessage;

    private String rawPayload;

    private PaymentProvider paymentProvider;
}
