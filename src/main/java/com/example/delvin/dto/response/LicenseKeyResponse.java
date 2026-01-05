package com.example.delvin.dto.response;

import com.example.delvin.enums.KeyStatus;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class LicenseKeyResponse {
    private Long id;
    private String keyCode;
    private KeyStatus status;
    private Long licenseProductId;
    private Instant createdAt;
}

