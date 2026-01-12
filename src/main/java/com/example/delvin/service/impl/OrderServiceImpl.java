package com.example.delvin.service.impl;

import com.example.delvin.config.apiconfig.AppException;
import com.example.delvin.config.apiconfig.ErrorCode;
import com.example.delvin.dto.request.OrderCreateRequest;
import com.example.delvin.entity.*;
import com.example.delvin.enums.KeyStatus;
import com.example.delvin.enums.OrderStatus;
import com.example.delvin.repository.*;
import com.example.delvin.service.OrderService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;


@Service
@AllArgsConstructor
public class OrderServiceImpl implements OrderService {
    private final OrderRepository orderRepository;
    private final OrderItemRepository orderItemRepository;
    private final UserRepository userRepository;
    private final LicenseKeyRepository licenseKeyRepository;
    private final UserWalletRepository userWalletRepository;


    public Order createOrder(OrderCreateRequest request) {

        User user = userRepository.findById(request.getUserId()).orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
        UserWallet wallet = userWalletRepository.findById(user.getWallet().getId()).orElseThrow(() -> new AppException(ErrorCode.WALLET_NOT_FOUND));
        LicenseKey licenseKey = licenseKeyRepository.findById(request.getLicenseKeyId()).orElseThrow(() -> new AppException(ErrorCode.LICENSE_KEY_NOT_FOUND));
        if(licenseKey.getStatus() != KeyStatus.SOLD && licenseKey.getSoldAt() == null) {
            throw new AppException(ErrorCode.LICENSE_KEY_NOT_SOLD);
        }

        PriceLicenseKey priceLicenseKey = licenseKey.getPriceLicenseKey();
        if(wallet.getBalance().compareTo(priceLicenseKey.getPrice()) < 0) {
            throw new AppException(ErrorCode.INSUFFICIENT_FUNDS);
        }
        BigDecimal totalAmount = (BigDecimal) request.getOrderItems().stream()
                .map(item ->
                        item.getUnitPrice()
                                .multiply(BigDecimal.valueOf(item.getQuantity()))
                )
                .reduce(BigDecimal.ZERO, BigDecimal::add);
        Order order = new Order();
        order.setCustomerEmail(request.getCustomerEmail());
        order.setCustomerPhone(request.getCustomerPhone());
        order.setTotalAmount(totalAmount);
        order.setStatus(OrderStatus.PAID);
        orderRepository.save(order);
        for (OrderItem item : request.getOrderItems()) {
            item.setOrder(order);
            item.setProduct(licenseKey.getLicenseProduct());
            item.setUnitPrice(priceLicenseKey.getPrice());
            item.setQuantity(1);
            orderItemRepository.save(item);
        }


        wallet.setBalance(wallet.getBalance().subtract(totalAmount));
        userWalletRepository.save(wallet);
        return order;
    }
}
