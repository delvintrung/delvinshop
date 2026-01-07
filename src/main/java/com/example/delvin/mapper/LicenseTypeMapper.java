package com.example.delvin.mapper;

import com.example.delvin.dto.response.LicenseTypeResponse;
import com.example.delvin.entity.LicenseType;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
 public interface LicenseTypeMapper {
    LicenseTypeResponse toResponse(LicenseType entity);
    List<LicenseTypeResponse> toResponseList(List<LicenseType> entities);
}
