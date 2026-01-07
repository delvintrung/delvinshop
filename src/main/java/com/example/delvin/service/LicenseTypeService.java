package com.example.delvin.service;

import com.example.delvin.dto.request.LicenseTypeRequest;
import com.example.delvin.dto.response.LicenseTypeResponse;
import com.example.delvin.entity.LicenseType;

import java.util.List;

public interface LicenseTypeService {
    LicenseTypeResponse createLicenseType(LicenseTypeRequest request);
    List<LicenseTypeResponse> getAllLicenseTypes();
    LicenseTypeResponse getLicenseTypeById(Long id);
    LicenseTypeResponse updateLicenseType(Long id, LicenseTypeRequest request);
    void deleteLicenseType(Long id);
}
