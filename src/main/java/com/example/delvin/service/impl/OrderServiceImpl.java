package com.example.delvin.service.impl;

import com.example.delvin.config.apiconfig.AppException;
import com.example.delvin.config.apiconfig.ErrorCode;
import com.example.delvin.dto.request.OrderCreateRequest;
import com.example.delvin.entity.LicenseKey;
import com.example.delvin.entity.Order;
import com.example.delvin.entity.User;
import com.example.delvin.entity.UserWallet;
import com.example.delvin.repository.*;
import com.example.delvin.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final LicenseKeyRepository licenseKeyRepository;
    private final UserWalletRepository userWalletRepository;


    public Order createOrder(OrderCreateRequest request) {
        // Implementation for creating an order
        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserWallet wallet = userWalletRepository.findById(user.getWallet().getId()).orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));
        LicenseKey licenseKey = licenseKeyRepository.findById(request.getLicenseKeyId()).orElseThrow(() -> new AppException(ErrorCode.LICENSE_KEY_NOT_FOUND));


        return null;
    }
}
