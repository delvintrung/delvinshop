package com.example.delvin.service;

import com.example.delvin.dto.request.GiftContentRequest;
import com.example.delvin.entity.GiftContent;

import java.util.List;

public interface GiftContentService {
    GiftContent createGiftContent(GiftContentRequest request);
    List<GiftContent> getAllGiftContents();
    GiftContent getGiftContentById(Long id);
    GiftContent updateGiftContent(Long id, GiftContentRequest request);
    void deleteGiftContent(Long id);
}
