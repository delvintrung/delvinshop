package com.example.delvin.service;

import com.example.delvin.dto.request.LicenseProductCreateRequest;
import com.example.delvin.entity.LicenseProduct;

import java.util.List;

public interface LicenseProductService {
    List<LicenseProduct> getAllLicenseProducts();
    LicenseProduct getLicenseProductById(Long id);
    LicenseProduct createLicenseProduct(LicenseProductCreateRequest licenseProductCreateRequest);
    LicenseProduct updateLicenseProduct(Long id, LicenseProductCreateRequest licenseProductCreateRequest);
    void deleteLicenseProduct(Long id);
}
