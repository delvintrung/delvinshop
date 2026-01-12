package com.example.delvin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceLicenseKeyResponse {
    private Long id;
    private String description;
    private BigDecimal price;

    private Instant createdAt;
    private Instant updatedAt;
}
