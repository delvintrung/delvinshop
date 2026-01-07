package com.example.delvin.mapper;

import com.example.delvin.dto.response.LicenseKeyResponse;
import com.example.delvin.entity.LicenseKey;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(
        componentModel = "spring",
        uses = {
                LicenseProductMapper.class,
                PriceLicenseKeyMapper.class,
                GiftContentMapper.class
        }
)
public interface LicenseKeyMapper {

    LicenseKeyResponse toResponse(LicenseKey entity);

    List<LicenseKeyResponse> toResponseList(List<LicenseKey> entities);
}

