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
import com.example.delvin.service.VnPayService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.Instant;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
@AllArgsConstructor
public class DepositTransactionServiceImpl implements DepositTransactionService {
    private final UserRepository userRepository;
    private final UserWalletRepository userWalletRepository;
    private final WalletTransactionRepository walletTransactionRepository;
    private final UserWalletService userWalletService;
    private final VnPayService vnPayService;
    private final DepositTransactionRepository depositTransactionRepository;

    @Override
    public DepositTransaction insertTransaction(DepositTransactionInsertRequest request) {
        return null;
    }

    @Transactional
    @Override
    public String createDepositTransaction(DepositTransactionInsertRequest request, HttpServletRequest httpRequest) {

        User user = userRepository.findById(request.getUserId())
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));

        String orderCode = String.valueOf(System.currentTimeMillis());

        DepositTransaction deposit = new DepositTransaction();
        deposit.setUser(user);
        deposit.setOrderCode(orderCode);
        deposit.setAmount(request.getAmount());
        deposit.setCurrency("VND");
        deposit.setPaymentProvider(request.getPaymentProvider());
        deposit.setStatus(DepositTransactionStatus.PENDING);
        depositTransactionRepository.save(deposit);

        return vnPayService.createPayment(
                orderCode,
                request.getAmount(),
                "DEPOSIT_" + orderCode,
                httpRequest
        );
    }

    @Transactional
    public void handleDepositCallback(DepositCallbackRequest callback) {


        DepositTransaction deposit = depositTransactionRepository
                .findByOrderCode(callback.getOrderCode())
                .orElse(null);

        if (deposit == null) {
            return;
        }

        if (deposit.getStatus() == DepositTransactionStatus.SUCCESS) {
            return;
        }

        if (!callback.isSuccess()) {
            deposit.setStatus(DepositTransactionStatus.FAILED);
            depositTransactionRepository.save(deposit);
            return;
        }

        Long userId = deposit.getUser().getId();
        UserWallet wallet = userWalletRepository
                .findByUserIdForUpdate(userId)
                .orElseGet(() -> {
                    UserWallet w = UserWallet.builder()
                            .user(deposit.getUser())
                            .balance(BigDecimal.ZERO)
                            .currency("VND")
                            .build();
                    return userWalletRepository.save(w);
                });

        WalletTransaction walletTx = new WalletTransaction();
        walletTx.setWallet(wallet);
        walletTx.setAmount(deposit.getAmount());
        walletTx.setType(WalletTransactionType.DEPOSIT);
        walletTx.setRefId(deposit.getId());
        walletTx.setBalanceAfter(wallet.getBalance());
        walletTransactionRepository.save(walletTx);


        wallet.setBalance(wallet.getBalance().add(deposit.getAmount()));
        userWalletRepository.save(wallet);



        deposit.setStatus(DepositTransactionStatus.SUCCESS);
        deposit.setExternalTxnId(callback.getExternalTxnId());
        deposit.setPaidAt(Instant.now());
        depositTransactionRepository.save(deposit);

    }

}
