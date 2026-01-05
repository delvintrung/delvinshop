package com.example.delvin.service.impl;

import com.example.delvin.config.apiconfig.AppException;
import com.example.delvin.config.apiconfig.ErrorCode;
import com.example.delvin.dto.request.LicenseProductCreateRequest;
import com.example.delvin.entity.LicenseProduct;
import com.example.delvin.entity.LicenseType;
import com.example.delvin.repository.LicenseProductRepository;
import com.example.delvin.repository.LicenseTypeRepository;
import com.example.delvin.service.LicenseProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LicenseProductServiceImpl implements LicenseProductService {
    private final LicenseProductRepository licenseProductRepository;
    private final LicenseTypeRepository licenseTypeRepository;

    @Override
    public List<LicenseProduct> getAllLicenseProducts() {
        return licenseProductRepository.findAll();
    }

    public LicenseProduct getLicenseProductById(Long id) {
        return licenseProductRepository.findById(id).orElse(null);
    }

    public LicenseProduct createLicenseProduct(LicenseProductCreateRequest licenseProductCreateRequest) {
        LicenseType licenseType = licenseTypeRepository.findById(licenseProductCreateRequest.getLicenseTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.LICENSE_TYPE_NOT_FOUND));
        LicenseProduct licenseProductEntity = new LicenseProduct();
        licenseProductEntity.setName(licenseProductCreateRequest.getName());
        licenseProductEntity.setDescription(licenseProductCreateRequest.getDescription());
        licenseProductEntity.setUseWith(licenseProductCreateRequest.getUseWith());
        licenseProductEntity.setLicenseType(licenseType);
        return licenseProductRepository.save(licenseProductEntity);
    }

    @Override
    public LicenseProduct updateLicenseProduct(Long id, LicenseProductCreateRequest licenseProductCreateRequest) {
        LicenseProduct existingProduct = licenseProductRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LICENSE_PRODUCT_NOT_FOUND));
        existingProduct.setName(licenseProductCreateRequest.getName());
        existingProduct.setDescription(licenseProductCreateRequest.getDescription());
        existingProduct.setUseWith(licenseProductCreateRequest.getUseWith());
        return licenseProductRepository.save(existingProduct);
    }

    @Override
    public void deleteLicenseProduct(Long id) {

    }
}
