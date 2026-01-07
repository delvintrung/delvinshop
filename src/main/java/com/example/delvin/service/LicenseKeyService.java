package com.example.delvin.service;

import com.example.delvin.dto.request.LicenseKeyCreateRequest;
import com.example.delvin.dto.response.LicenseKeyResponse;
import com.example.delvin.entity.LicenseKey;

import java.util.List;

public interface LicenseKeyService {
    List<LicenseKeyResponse> getAllLicenseKeys();
    LicenseKeyResponse getLicenseKeyById(Long id);
    LicenseKeyResponse createLicenseKey(LicenseKeyCreateRequest request);
    LicenseKeyResponse updateLicenseKeyStatus(Long id, com.example.delvin.enums.KeyStatus status);
}
