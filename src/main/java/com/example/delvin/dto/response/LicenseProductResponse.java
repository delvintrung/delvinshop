package com.example.delvin.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
 public class LicenseProductResponse {
    private Long id;
    private String name;
    private String description;

    private LicenseTypeResponse licenseType;

    private Instant createdAt;
    private Instant updatedAt;

}
