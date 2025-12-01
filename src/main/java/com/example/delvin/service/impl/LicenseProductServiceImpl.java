package com.example.delvin.service.impl;

import com.example.delvin.dto.request.LicenseProductCreateRequest;
import com.example.delvin.entity.LicenseProduct;
import com.example.delvin.repository.LicenseProductRepository;
import com.example.delvin.service.LicenseProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LicenseProductServiceImpl implements LicenseProductService {
    private final LicenseProductRepository licenseProductRepository;

    @Override
    public List<LicenseProduct> getAllLicenseProducts() {
        return licenseProductRepository.findAll();
    }

    public LicenseProduct getLicenseProductById(Long id) {
        return licenseProductRepository.findById(id).orElse(null);
    }

    public LicenseProduct createLicenseProduct(LicenseProductCreateRequest licenseProductCreateRequest) {
        LicenseProduct licenseProductEntity = new LicenseProduct();
        licenseProductEntity.setName(licenseProductCreateRequest.getName());
        licenseProductEntity.setDescription(licenseProductCreateRequest.getDescription());
        licenseProductEntity.setPrice(licenseProductCreateRequest.getPrice());
        // Assuming expireAt is in ISO_LOCAL_DATE_TIME format
        licenseProductEntity.setExpireAt(java.time.LocalDateTime.parse(licenseProductCreateRequest.getExpireAt()));
        return licenseProductRepository.save(licenseProductEntity);
    }
}
