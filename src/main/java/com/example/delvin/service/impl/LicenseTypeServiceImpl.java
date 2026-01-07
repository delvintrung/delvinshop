package com.example.delvin.service.impl;


import com.example.delvin.config.apiconfig.AppException;
import com.example.delvin.config.apiconfig.ErrorCode;
import com.example.delvin.dto.request.LicenseTypeRequest;
import com.example.delvin.dto.response.LicenseTypeResponse;
import com.example.delvin.entity.LicenseType;
import com.example.delvin.mapper.LicenseTypeMapper;
import com.example.delvin.repository.LicenseTypeRepository;
import com.example.delvin.service.LicenseTypeService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class LicenseTypeServiceImpl implements LicenseTypeService {
    private final LicenseTypeRepository licenseTypeRepository;
    private final LicenseTypeMapper licenseTypeMapper;

    @Override
    public LicenseTypeResponse createLicenseType(LicenseTypeRequest request) {

        if (licenseTypeRepository.existsByTypeName(request.getTypeName())) {
            throw new AppException(ErrorCode.LICENSE_TYPE_EXISTED);
        }
        LicenseType licenseType = new LicenseType();
        licenseType.setTypeName(request.getTypeName());
        licenseTypeRepository.save(licenseType);
        return licenseTypeMapper.toResponse(licenseType);
    }

    @Override
    public LicenseTypeResponse getLicenseTypeById(Long id) {
        LicenseType licenseType = licenseTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LICENSE_TYPE_NOT_FOUND));
        return licenseTypeMapper.toResponse(licenseType);
    }

    @Override
    public List<LicenseTypeResponse> getAllLicenseTypes()  {
        List<LicenseType> licenseTypeList = licenseTypeRepository.findAll();
        return licenseTypeMapper.toResponseList(licenseTypeList);
    }

    @Override
    public LicenseTypeResponse updateLicenseType(Long id, LicenseTypeRequest request) {
        LicenseType existingLicenseType = licenseTypeRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LICENSE_TYPE_NOT_FOUND));
        if(request.getTypeName() != null)
        {existingLicenseType.setTypeName(request.getTypeName());}
        LicenseType licenseType =  licenseTypeRepository.save(existingLicenseType);
        return licenseTypeMapper.toResponse(licenseType);
    }

    @Override
    public void deleteLicenseType(Long id) {
        if (!licenseTypeRepository.existsById(id)) {
            throw new AppException(ErrorCode.LICENSE_TYPE_NOT_FOUND);
        }
        licenseTypeRepository.deleteById(id);
    }

}
