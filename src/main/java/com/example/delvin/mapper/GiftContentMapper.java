package com.example.delvin.mapper;

import com.example.delvin.dto.response.GiftContentResponse;
import com.example.delvin.entity.GiftContent;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface GiftContentMapper {
    GiftContentResponse toResponse(GiftContent entity);
}

