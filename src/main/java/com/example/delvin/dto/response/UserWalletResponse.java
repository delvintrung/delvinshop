package com.example.delvin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class UserWalletResponse {
    private Long id;
    private Double balance;
    private String currency;
    private Instant updatedAt;
}
