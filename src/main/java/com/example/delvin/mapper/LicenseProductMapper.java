package com.example.delvin.mapper;

import com.example.delvin.dto.response.LicenseKeyResponse;
import com.example.delvin.dto.response.LicenseProductResponse;
import com.example.delvin.entity.LicenseProduct;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(componentModel = "spring",
        uses = {LicenseTypeMapper.class})
public interface  LicenseProductMapper {
    LicenseProductResponse toResponse(LicenseProduct entity);
    List<LicenseProductResponse> toResponseList(List<LicenseProduct> entities);
}

