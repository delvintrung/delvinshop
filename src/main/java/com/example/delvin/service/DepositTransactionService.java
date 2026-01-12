package com.example.delvin.service;

import com.example.delvin.dto.request.DepositCallbackRequest;
import com.example.delvin.dto.request.DepositTransactionInsertRequest;
import com.example.delvin.entity.DepositTransaction;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;

public interface DepositTransactionService {
    @Transactional
    DepositTransaction insertTransaction(DepositTransactionInsertRequest request);

    @Transactional
    String createDepositTransaction(DepositTransactionInsertRequest request, HttpServletRequest httpRequest);
    @Transactional
    void handleDepositCallback(DepositCallbackRequest callback);
}
