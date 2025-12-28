package com.example.delvin.service.impl;


import com.example.delvin.config.apiconfig.AppException;
import com.example.delvin.config.apiconfig.ErrorCode;
import com.example.delvin.dto.request.LicenseTypeRequest;
import com.example.delvin.entity.LicenseType;
import com.example.delvin.repository.LicenseTypeRepository;
import com.example.delvin.service.LicenseTypeService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LicenseTypeServiceImpl implements LicenseTypeService {
    private final LicenseTypeRepository licenseTypeRepository;

    @Override
    public LicenseType createLicenseType(LicenseTypeRequest request) {

        if (licenseTypeRepository.existsByTypeName(request.getTypeName())) {
            throw new AppException(ErrorCode.LICENSE_TYPE_EXISTED);
        }
        LicenseType licenseType = new LicenseType();
        licenseType.setTypeName(request.getTypeName());
        return licenseTypeRepository.save(licenseType);
    }

    @Override
    public LicenseType getLicenseTypeById(Long id) {
        return licenseTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LICENSE_TYPE_NOT_FOUND));
    }

    @Override
            public List<LicenseType> getAllLicenseTypes() {
        return licenseTypeRepository.findAll();
    }

    @Override
    public LicenseType updateLicenseType(Long id, LicenseTypeRequest request) {
        LicenseType existingLicenseType = licenseTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LICENSE_TYPE_NOT_FOUND));
        existingLicenseType.setTypeName(request.getTypeName());
        return licenseTypeRepository.save(existingLicenseType);
    }

    @Override
    public void deleteLicenseType(Long id) {
        if (!licenseTypeRepository.existsById(id)) {
            throw new AppException(ErrorCode.LICENSE_TYPE_NOT_FOUND);
        }
        licenseTypeRepository.deleteById(id);
    }

    LicenseTypeServiceImpl(LicenseTypeRepository licenseTypeRepository) {
        this.licenseTypeRepository = licenseTypeRepository;
    }
}
