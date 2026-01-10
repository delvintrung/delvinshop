package com.example.delvin.service.impl;

import com.example.delvin.config.apiconfig.AppException;
import com.example.delvin.config.apiconfig.ErrorCode;
import com.example.delvin.dto.request.DepositCallbackRequest;
import com.example.delvin.dto.request.DepositTransactionInsertRequest;
import com.example.delvin.entity.DepositTransaction;
import com.example.delvin.entity.User;
import com.example.delvin.entity.UserWallet;
import com.example.delvin.entity.WalletTransaction;
import com.example.delvin.enums.DepositTransactionStatus;
import com.example.delvin.enums.WalletTransactionType;
import com.example.delvin.repository.DepositTransactionRepository;
import com.example.delvin.repository.UserRepository;
import com.example.delvin.repository.UserWalletRepository;
import com.example.delvin.repository.WalletTransactionRepository;
import com.example.delvin.service.DepositTransactionService;
import com.example.delvin.service.UserWalletService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;

@Service
@AllArgsConstructor
public class DepositTransactionServiceImpl implements DepositTransactionService {
    private final UserRepository userRepository;
    private final UserWalletRepository userWalletRepository;
    private final WalletTransactionRepository walletTransactionRepository;
    private final UserWalletService userWalletService;
    private final DepositTransactionRepository depositTransactionRepository;

    @Override
    public DepositTransaction insertTransaction(DepositTransactionInsertRequest request) {
        return null;
    }

    @Transactional
    @Override
    public DepositTransaction createDepositTransaction(DepositTransactionInsertRequest request) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        DepositTransaction deposit = new DepositTransaction();
        deposit.setUser(user);
        deposit.setAmount(request.getAmount());
        deposit.setCurrency("VND");
        deposit.setPaymentProvider(request.getPaymentProvider());
        deposit.setOrderCode(UUID.randomUUID().toString());
        deposit.setStatus(DepositTransactionStatus.PENDING);

        return depositTransactionRepository.save(deposit);
    }

    @Transactional
    public void handleDepositCallback(DepositCallbackRequest callback) {

        DepositTransaction deposit = depositTransactionRepository
                .findByOrderCode(callback.getOrderCode())
                .orElseThrow(() -> new AppException(ErrorCode.DEPOSIT_NOT_FOUND));

        // 🔒 idempotent
        if (deposit.getStatus() == DepositTransactionStatus.SUCCESS) {
            return;
        }

        // ❌ nếu thất bại
        if (!callback.isSuccess()) {
            deposit.setStatus(DepositTransactionStatus.FAILED);
            depositTransactionRepository.save(deposit);
            return;
        }

        UserWallet wallet = userWalletRepository.findByUserIdForUpdate(
                deposit.getUser().getId()
        );

        wallet.setBalance(wallet.getBalance().add(deposit.getAmount()));
        userWalletRepository.save(wallet);

        WalletTransaction walletTx = new WalletTransaction();
        walletTx.setWallet(wallet);
        walletTx.setAmount(deposit.getAmount());
        walletTx.setType(WalletTransactionType.DEPOSIT);
        walletTx.setRefId(deposit.getId());
        walletTransactionRepository.save(walletTx);

        // ✅ cập nhật giao dịch
        deposit.setStatus(DepositTransactionStatus.SUCCESS);
        deposit.setExternalTxnId(callback.getExternalTxnId());
        deposit.setPaidAt(Instant.now());
        depositTransactionRepository.save(deposit);
    }


}
