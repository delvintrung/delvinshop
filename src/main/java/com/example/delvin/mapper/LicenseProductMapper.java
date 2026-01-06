package com.example.delvin.mapper;

import com.example.delvin.dto.response.LicenseProductResponse;
import com.example.delvin.entity.LicenseProduct;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LicenseProductMapper {
    LicenseProductResponse toResponse(LicenseProduct entity);
}

