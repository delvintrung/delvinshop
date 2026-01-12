package com.example.delvin.controller;

import com.example.delvin.dto.request.DepositCallbackRequest;
import com.example.delvin.dto.request.DepositTransactionInsertRequest;
import com.example.delvin.enums.PaymentProvider;
import com.example.delvin.mapper.VnpayMapper;
import com.example.delvin.service.DepositTransactionService;
import com.example.delvin.service.MomoService;
import com.example.delvin.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final MomoService momoService;
    private final VnPayService vnPayService;
    private final HttpServletRequest httpServletRequest;
    private final DepositTransactionService depositTransactionService;
    private final VnpayMapper vnPayMapper;
    private final DepositTransactionService depositService;
    // Frontend gọi: POST /api/v1/payment/create-momo?amount=50000&orderInfo=MuaAo
    @PostMapping("/create-momo")
    public ResponseEntity<Map<String, Object>> createMomoPayment(
            @RequestParam String amount,
            @RequestParam String orderInfo
    ) {
        String orderId = "DELVIN_" + System.currentTimeMillis();

        Map<String, Object> result = momoService.createPayment(orderId, amount, orderInfo);

        return ResponseEntity.ok(result);
    }

    @GetMapping("/momo-return")
    public ResponseEntity<String> momoReturn(@RequestParam Map<String, String> params) {
        String resultCode = params.get("resultCode");
        String message = params.get("message");

        if ("0".equals(resultCode)) {
            return ResponseEntity.ok("Giao dịch thành công! Mã đơn: " + params.get("orderId"));
        } else {
            return ResponseEntity.badRequest().body("Giao dịch thất bại: " + message);
        }
    }

    @PostMapping("/create-vnpay")
    public ResponseEntity<String> createVnPayPayment(
            @RequestParam Long amount,
            @RequestParam String userId,
            HttpServletRequest httpRequest
    ) {
        DepositTransactionInsertRequest request = new DepositTransactionInsertRequest();
        request.setPaymentProvider(PaymentProvider.VNPAY);
        request.setCurrency("VND");
        request.setAmount(BigDecimal.valueOf(amount));
        request.setUserId(Long.valueOf(userId));

        String paymentUrl = depositTransactionService.createDepositTransaction(request, httpRequest);

        return ResponseEntity.ok(paymentUrl);
    }

    @GetMapping("/vnpay-return")
    public ResponseEntity<String> vnpayReturn(@RequestParam Map<String, String> params) {
        String responseCode = params.get("vnp_ResponseCode");
        String message = params.get("vnp_Message");
        if ("00".equals(responseCode)) {

            return ResponseEntity.ok("Giao dịch VNPAY thành công! Mã đơn: " + params.get("vnp_TxnRef"));
        } else {
            return ResponseEntity.badRequest().body("Giao dịch VNPAY thất bại: " + message);
        }
    }

    @GetMapping("/vnpay-ipn")
    public ResponseEntity<String> vnpayIpn(@RequestParam Map<String, String> params) {


        boolean valid = vnPayService.verifySignature(params);

        if (!valid) {
            return ResponseEntity.ok("INVALID_SIGNATURE");
        }

        DepositCallbackRequest callback = vnPayMapper.fromVnPay(params);


        depositService.handleDepositCallback(callback);

        return ResponseEntity.ok("OK");
    }


}
