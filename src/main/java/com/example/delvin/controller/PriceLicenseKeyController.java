package com.example.delvin.controller;

import com.example.delvin.config.apiconfig.ApiResponse;
import com.example.delvin.config.apiconfig.AppException;
import com.example.delvin.config.apiconfig.ErrorCode;
import com.example.delvin.dto.request.PriceLicenseKeyCreateRequest;
import com.example.delvin.dto.response.PriceLicenseKeyResponse;
import com.example.delvin.entity.PriceLicenseKey;
import com.example.delvin.repository.PriceLicenseKeyRepository;
import com.example.delvin.service.PriceLicenseKeyService;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/price-license-keys")
@AllArgsConstructor
public class PriceLicenseKeyController {
    private final PriceLicenseKeyService priceLicenseKeyService;
    private final PriceLicenseKeyRepository priceLicenseKeyRepository;

    @GetMapping
    public ApiResponse<List<PriceLicenseKeyResponse>> getAllPriceLicenseKeys() {
        List<PriceLicenseKeyResponse> responses =
                priceLicenseKeyService.getAllPriceLicenseKeys()
                        .stream()
                        .map(price -> new PriceLicenseKeyResponse(
                                price.getId(),
                                price.getDescription(),
                                price.getPrice(),
                                price.getCreatedAt(),
                                price.getUpdatedAt()
                        ))
                        .toList();
        return ApiResponse.<List<PriceLicenseKeyResponse>>builder()
                .result(responses)
                .message("Lấy danh sách giá license key thành công")
                .build();
    }

    @GetMapping("{id}")
    public ApiResponse<PriceLicenseKey> getPriceLicenseKeyById(@PathVariable Long id) {
        PriceLicenseKey priceLicenseKey = priceLicenseKeyService.getPriceLicenseKeyById(id);
        return ApiResponse.<PriceLicenseKey>builder()
                .result(priceLicenseKey)
                .message("Lấy giá license key thành công")
                .build();
    }

    @PostMapping
    public ApiResponse<PriceLicenseKey> createPriceLicenseKey(@RequestBody PriceLicenseKeyCreateRequest request) {
        PriceLicenseKey createdPriceLicenseKey = priceLicenseKeyService.createPriceLicenseKey(request);
        return ApiResponse.<PriceLicenseKey>builder()
                .result(createdPriceLicenseKey)
                .message("Tạo giá license key thành công")
                .build();
    }

    @PatchMapping("{id}")
    public ApiResponse<PriceLicenseKey> updatePriceLicenseKey(@PathVariable Long id,@RequestBody PriceLicenseKeyCreateRequest request) {
        PriceLicenseKey updatedPriceLicenseKey = priceLicenseKeyService.updatePriceLicenseKey(id, request);
        return ApiResponse.<PriceLicenseKey>builder()
                .result(updatedPriceLicenseKey)
                .message("Cập nhật giá license key thành công")
                .build();
    }

    @DeleteMapping("{id}")
    public void deletePriceLicenseKey(@PathVariable Long id) {
        if (!priceLicenseKeyRepository.existsById(id)) {
            throw new AppException(ErrorCode.LICENSE_KEY_NOT_FOUND);
        }
        priceLicenseKeyService.deletePriceLicenseKey(id);
    }

}
