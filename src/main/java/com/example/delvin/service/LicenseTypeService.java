package com.example.delvin.service;

import com.example.delvin.dto.request.LicenseTypeRequest;
import com.example.delvin.entity.LicenseType;

import java.util.List;

public interface LicenseTypeService {
    LicenseType createLicenseType(LicenseTypeRequest request);
    List<LicenseType> getAllLicenseTypes();
    LicenseType getLicenseTypeById(Long id);
    LicenseType updateLicenseType(Long id, LicenseTypeRequest request);
    void deleteLicenseType(Long id);
}
