package com.example.delvin.dto.request;

import com.example.delvin.enums.LicenseKeyPrefix;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
public class LicenseKeyCreateRequest {
    private Long licenseProductId;
    private LicenseKeyPrefix typePrefix;
}
