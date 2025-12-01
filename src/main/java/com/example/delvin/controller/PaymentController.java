package com.example.delvin.controller;

import com.example.delvin.service.MomoService;
import com.example.delvin.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/payment")
@RequiredArgsConstructor
public class PaymentController {
    private final MomoService momoService;
    private final VnPayService vnPayService;
    private final HttpServletRequest httpServletRequest;
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
            @RequestParam String orderInfo
    ) {
        String orderId = "DELVIN_VNPAY_" + System.currentTimeMillis();

        String paymentUrl = vnPayService.createPayment(amount, orderId, httpServletRequest);

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
}
