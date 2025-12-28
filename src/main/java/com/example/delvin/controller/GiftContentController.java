package com.example.delvin.controller;

import com.example.delvin.config.apiconfig.ApiResponse;
import com.example.delvin.dto.request.GiftContentRequest;
import com.example.delvin.entity.GiftContent;
import com.example.delvin.service.GiftContentService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/gift-contents")
public class GiftContentController {
    private final GiftContentService giftContentService;

    public GiftContentController(GiftContentService giftContentService) {
        this.giftContentService = giftContentService;
    }

    @GetMapping
    public ApiResponse<List<GiftContent>> getAll() {
        List<GiftContent> giftContents = giftContentService.getAllGiftContents();
        return ApiResponse.<List<GiftContent>>builder()
                .result(giftContents)
                .message("Lấy danh sách nội dung quà tặng thành công")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<GiftContent> getById(@PathVariable Long id) {
        GiftContent giftContent = giftContentService.getGiftContentById(id);
        return ApiResponse.<GiftContent>builder()
                .result(giftContent)
                .message("Lấy nội dung quà tặng thành công")
                .build();
    }

    @PostMapping
    public ApiResponse<GiftContent> create(@RequestBody GiftContentRequest request) {
        GiftContent createdGiftContent = giftContentService.createGiftContent(request);
        return ApiResponse.<GiftContent>builder()
                .result(createdGiftContent)
                .message("Tạo nội dung quà tặng thành công")
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<GiftContent> update(@PathVariable Long id, @RequestBody GiftContentRequest request) {
        GiftContent updatedGiftContent = giftContentService.updateGiftContent(id, request);
        return ApiResponse.<GiftContent>builder()
                .result(updatedGiftContent)
                .message("Cập nhật nội dung quà tặng thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> delete(@PathVariable Long id) {
        giftContentService.deleteGiftContent(id);
        return ApiResponse.<Void>builder()
                .message("Xóa nội dung quà tặng thành công")
                .build();
    }
}
