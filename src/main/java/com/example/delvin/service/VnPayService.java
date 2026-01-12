package com.example.delvin.service;

import jakarta.servlet.http.HttpServletRequest;

import java.math.BigDecimal;
import java.util.Map;

public interface VnPayService {
    String createPayment(String orderCode,BigDecimal amount, String orderInfo, HttpServletRequest request);
    boolean verifySignature(Map<String, String> params);
}
