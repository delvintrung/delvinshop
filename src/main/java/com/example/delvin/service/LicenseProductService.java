package com.example.delvin.service;

import com.example.delvin.dto.request.LicenseProductCreateRequest;
import com.example.delvin.dto.response.LicenseProductResponse;
import com.example.delvin.entity.LicenseProduct;

import java.util.List;

public interface LicenseProductService {
    List<LicenseProductResponse> getAllLicenseProducts();
    LicenseProductResponse getLicenseProductById(Long id);
    LicenseProductResponse createLicenseProduct(LicenseProductCreateRequest licenseProductCreateRequest);
    LicenseProductResponse updateLicenseProduct(Long id, LicenseProductCreateRequest licenseProductCreateRequest);
    void deleteLicenseProduct(Long id);
}
