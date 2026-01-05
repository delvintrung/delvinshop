package com.example.delvin.service;

import com.example.delvin.dto.request.LicenseKeyCreateRequest;
import com.example.delvin.entity.LicenseKey;

public interface LicenseKeyService {
    LicenseKey createLicenseKey(LicenseKeyCreateRequest request);
    LicenseKey updateLicenseKeyStatus(Long id, com.example.delvin.enums.KeyStatus status);
}
