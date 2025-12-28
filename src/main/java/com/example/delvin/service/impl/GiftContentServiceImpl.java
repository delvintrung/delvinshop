package com.example.delvin.service.impl;

import com.example.delvin.config.apiconfig.AppException;
import com.example.delvin.config.apiconfig.ErrorCode;
import com.example.delvin.dto.request.GiftContentRequest;
import com.example.delvin.entity.GiftContent;
import com.example.delvin.repository.GiftContentRepository;
import com.example.delvin.service.GiftContentService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GiftContentServiceImpl implements GiftContentService {
    private final GiftContentRepository giftContentRepository;

    public GiftContentServiceImpl(GiftContentRepository giftContentRepository) {
        this.giftContentRepository = giftContentRepository;
    }

    @Override
    public GiftContent createGiftContent(GiftContentRequest request) {
        GiftContent giftContent = new GiftContent();
        giftContent.setSenderName(request.getSenderName());
        giftContent.setMessage(request.getMessage());
        giftContent.setRecipientName(request.getRecipientName());
        giftContent.setBackgroundMusic(request.getBackgroundMusic());
        giftContent.setImageUrl(request.getImageUrl());
        return giftContentRepository.save(giftContent);
    }

    @Override
    public List<GiftContent> getAllGiftContents() {
        List<GiftContent> giftContents = giftContentRepository.findAll();
        return giftContents;
    }

    @Override
    public GiftContent getGiftContentById(Long id) {
        GiftContent giftContent = giftContentRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.GIFTCONTENT_NOT_FOUND));
        return giftContent;
    }

    @Override
    public GiftContent updateGiftContent(Long id, GiftContentRequest request) {
        GiftContent existingGiftContent = giftContentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.GIFTCONTENT_NOT_FOUND));
        existingGiftContent.setSenderName(request.getSenderName());
        existingGiftContent.setMessage(request.getMessage());
        existingGiftContent.setRecipientName(request.getRecipientName());
        existingGiftContent.setBackgroundMusic(request.getBackgroundMusic());
        existingGiftContent.setImageUrl(request.getImageUrl());
        return giftContentRepository.save(existingGiftContent);
    }

    @Override
    public void deleteGiftContent(Long id) {
        GiftContent existingGiftContent = giftContentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.GIFTCONTENT_NOT_FOUND));
        giftContentRepository.delete(existingGiftContent);
    }
}
