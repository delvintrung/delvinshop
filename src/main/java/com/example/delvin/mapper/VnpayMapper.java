package com.example.delvin.mapper;


import com.example.delvin.dto.request.DepositCallbackRequest;
import com.example.delvin.enums.PaymentProvider;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@Component
public class VnpayMapper {

    public DepositCallbackRequest fromVnPay(Map<String, String> params) {
         log.error(params.get("vnp_TxnRef"));

        DepositCallbackRequest dto = new DepositCallbackRequest();

        dto.setOrderCode(params.get("vnp_TxnRef"));
        dto.setExternalTxnId(params.get("vnp_TransactionNo"));

        String responseCode = params.get("vnp_ResponseCode");
        dto.setResponseCode(responseCode);
        dto.setSuccess("00".equals(responseCode));

        dto.setAmount(
                new BigDecimal(params.get("vnp_Amount"))
                        .divide(BigDecimal.valueOf(100))
        );

        dto.setPaymentProvider(PaymentProvider.VNPAY);

        dto.setRawPayload(
                params.entrySet().stream()
                        .map(e -> e.getKey() + "=" + e.getValue())
                        .collect(Collectors.joining("&"))
        );

        return dto;
    }
}


