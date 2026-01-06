package com.example.delvin.mapper;

import com.example.delvin.dto.response.LicenseTypeResponse;
import com.example.delvin.entity.LicenseType;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
 public interface LicenseTypeMapper {
    LicenseTypeResponse toResponse(LicenseType entity);
}
