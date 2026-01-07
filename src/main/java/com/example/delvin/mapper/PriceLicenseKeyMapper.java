package com.example.delvin.mapper;

import com.example.delvin.dto.response.PriceLicenseKeyResponse;
import com.example.delvin.entity.PriceLicenseKey;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PriceLicenseKeyMapper {
    PriceLicenseKeyResponse toResponse(PriceLicenseKey entity);
    List<PriceLicenseKeyResponse> toResponseList(List<PriceLicenseKey> entities);
}

