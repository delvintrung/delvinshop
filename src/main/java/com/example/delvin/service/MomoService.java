package com.example.delvin.service;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Map;

public interface MomoService {
    Map<String, Object> createPayment(String orderId, String amount, String orderInfo);
    String hmacSHA256(String data, String key) throws NoSuchAlgorithmException, InvalidKeyException;
}
