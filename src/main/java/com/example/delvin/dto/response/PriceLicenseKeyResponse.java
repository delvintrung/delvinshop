package com.example.delvin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PriceLicenseKeyResponse {
    private Long id;
    private String description;
    private Double price;

    private Instant createdAt;
    private Instant updatedAt;
}
