package com.example.delvin.service.impl;

import com.example.delvin.repository.OrderItemRepository;
import com.example.delvin.service.OrderItemService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class OrderItemServiceImpl implements OrderItemService {
    private final OrderItemRepository orderItemRepository;

}
