package com.example.delvin.service;

import com.example.delvin.dto.request.DepositTransactionInsertRequest;
import com.example.delvin.entity.DepositTransaction;
import jakarta.transaction.Transactional;

public interface DepositTransactionService {
    @Transactional
    DepositTransaction insertTransaction(DepositTransactionInsertRequest request);

    @Transactional
    DepositTransaction createDepositTransaction(DepositTransactionInsertRequest request);
}
