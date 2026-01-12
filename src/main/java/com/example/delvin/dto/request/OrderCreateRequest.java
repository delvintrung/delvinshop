package com.example.delvin.dto.request;

import com.example.delvin.entity.OrderItem;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class OrderCreateRequest {
    private Long userId;
    private Long productId;
    private Long licenseKeyId;
    private String customerEmail;
    private String customerPhone;
    private List<OrderItem> orderItems;
}
