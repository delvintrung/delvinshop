package com.example.delvin.service;

import jakarta.servlet.http.HttpServletRequest;

public interface VnPayService {
    String createPayment(long amount, String orderInfo, HttpServletRequest request);
}
