package com.example.delvin.service.impl;

import com.example.delvin.config.apiconfig.AppException;
import com.example.delvin.config.apiconfig.ErrorCode;
import com.example.delvin.dto.request.LicenseProductCreateRequest;
import com.example.delvin.dto.response.LicenseProductResponse;
import com.example.delvin.entity.LicenseProduct;
import com.example.delvin.entity.LicenseType;
import com.example.delvin.mapper.LicenseProductMapper;
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
    private final LicenseProductMapper licenseProductMapper;

    @Override
    public List<LicenseProductResponse> getAllLicenseProducts() {
       List<LicenseProduct> listProducts =  licenseProductRepository.findAll();
       return licenseProductMapper.toResponseList(listProducts);
    }

    public LicenseProductResponse getLicenseProductById(Long id) {
        LicenseProduct licenseProduct = licenseProductRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.LICENSE_PRODUCT_NOT_FOUND));
        return licenseProductMapper.toResponse(licenseProduct);
    }

    public LicenseProductResponse createLicenseProduct(LicenseProductCreateRequest licenseProductCreateRequest) {
        LicenseType licenseType = licenseTypeRepository.findById(licenseProductCreateRequest.getLicenseTypeId())
                .orElseThrow(() -> new AppException(ErrorCode.LICENSE_TYPE_NOT_FOUND));
        LicenseProduct licenseProductEntity = new LicenseProduct();
        licenseProductEntity.setName(licenseProductCreateRequest.getName());
        licenseProductEntity.setDescription(licenseProductCreateRequest.getDescription());
        licenseProductEntity.setUseWith(licenseProductCreateRequest.getUseWith());
        licenseProductEntity.setLicenseType(licenseType);
        LicenseProduct licenseProduct = licenseProductRepository.save(licenseProductEntity);
        return licenseProductMapper.toResponse(licenseProduct);
    }

    @Override
    public LicenseProductResponse updateLicenseProduct(
            Long id,
            LicenseProductCreateRequest request
    ) {
        LicenseProduct existingProduct = licenseProductRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LICENSE_PRODUCT_NOT_FOUND));

        if (request.getName() != null) {
            existingProduct.setName(request.getName());
        }

        if (request.getDescription() != null) {
            existingProduct.setDescription(request.getDescription());
        }

        if (request.getUseWith() != null) {
            existingProduct.setUseWith(request.getUseWith());
        }

        return licenseProductMapper.toResponse(
                licenseProductRepository.save(existingProduct)
        );
    }


    @Override
    public void deleteLicenseProduct(Long id) {

    }
}
