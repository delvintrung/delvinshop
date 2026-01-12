package com.example.delvin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceLicenseKeyCreateRequest {
    private String description;
    private BigDecimal price;
}
