package com.example.delvin.mapper;

import com.example.delvin.dto.response.GiftContentResponse;
import com.example.delvin.entity.GiftContent;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface GiftContentMapper {
    GiftContentResponse toResponse(GiftContent entity);
    List<GiftContentResponse> toResponseList(List<GiftContent> entities);
}

