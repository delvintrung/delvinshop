package com.example.delvin.service.impl;

import com.example.delvin.config.VnPayConfig;
import com.example.delvin.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;
import lombok.extern.slf4j.Slf4j;


@Slf4j
@Service
@RequiredArgsConstructor
public class VnPayServiceImpl implements VnPayService {
    private final VnPayConfig vnPayConfig;
    private static final String HMAC_SHA512 = "HmacSHA512";
    @Value("${vnpay.hash-secret}")
    private String hashSecret;


    @Override
    public String createPayment(String orderCode,
                                BigDecimal amount, String orderInfo, HttpServletRequest request) {
        String vnp_Version = "2.1.0";
        String vnp_Command = "pay";
        String vnp_IpAddr = VnPayConfig.getIpAddress(request);
        String vnp_TmnCode = vnPayConfig.getVnp_TmnCode();

        String vnp_TxnRef = orderCode;
        long amountVal = amount.multiply(BigDecimal.valueOf(100)).longValue();

        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", vnp_Version);
        vnp_Params.put("vnp_Command", vnp_Command);
        vnp_Params.put("vnp_TmnCode", vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amountVal));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", orderInfo);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", vnPayConfig.getVnp_ReturnUrl());
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);

        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);

        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator<String> itr = fieldNames.iterator();

        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                // Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII));

                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        String queryUrl = query.toString();
        String vnp_SecureHash = vnPayConfig.hmacSHA512(vnPayConfig.getSecretKey(), hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;

        return vnPayConfig.getVnp_Url() + "?" + queryUrl;
    }

    public boolean verifySignature(Map<String, String> params) {

        String vnpSecureHash = params.get("vnp_SecureHash");
        if (vnpSecureHash == null) return false;

        Map<String, String> filteredParams = new TreeMap<>();

        params.forEach((key, value) -> {
            if (!key.equals("vnp_SecureHash")
                    && !key.equals("vnp_SecureHashType")
                    && value != null
                    && !value.isEmpty()) {
                filteredParams.put(key, value);
            }
        });

        StringBuilder hashData = new StringBuilder();
        filteredParams.forEach((key, value) -> {
            hashData.append(key)
                    .append("=")
                    .append(URLEncoder.encode(value, StandardCharsets.UTF_8))
                    .append("&");
        });

        hashData.deleteCharAt(hashData.length() - 1);

        String calculatedHash = hmacSHA512(
                vnPayConfig.getSecretKey(),
                hashData.toString()
        );

        log.error("🔐 HASH DATA = {}", hashData);
        log.error("🔐 HASH CALC = {}", calculatedHash);
        log.error("🔐 HASH VNPAY = {}", vnpSecureHash);

        return calculatedHash.equalsIgnoreCase(vnpSecureHash);
    }



    private String buildQueryString(Map<String, String> params) {

        List<String> fieldNames = new ArrayList<>(params.keySet());
        Collections.sort(fieldNames);

        StringBuilder sb = new StringBuilder();
        boolean first = true;

        for (String fieldName : fieldNames) {
            String value = params.get(fieldName);
            if (value != null && !value.isEmpty()) {
                if (!first) {
                    sb.append("&");
                }
                first = false;

                sb.append(fieldName);
                sb.append("=");
                sb.append(URLEncoder.encode(value, StandardCharsets.UTF_8));
            }
        }
        return sb.toString();
    }

    public static String hmacSHA512(String key, String data) {
        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKey =
                    new SecretKeySpec(key.getBytes(StandardCharsets.UTF_8), "HmacSHA512");
            mac.init(secretKey);
            byte[] raw = mac.doFinal(data.getBytes(StandardCharsets.UTF_8));
            return HexFormat.of().formatHex(raw);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }


    private String bytesToHex(byte[] bytes) {
        StringBuilder hex = new StringBuilder(bytes.length * 2);
        for (byte b : bytes) {
            String st = Integer.toHexString(0xff & b);
            if (st.length() == 1) hex.append('0');
            hex.append(st);
        }
        return hex.toString();
    }

}
