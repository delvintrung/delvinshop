package com.example.delvin.controller;

import com.example.delvin.config.apiconfig.ApiResponse;
import com.example.delvin.dto.request.LicenseKeyCreateRequest;
import com.example.delvin.dto.response.GiftContentResponse;
import com.example.delvin.dto.response.LicenseKeyResponse;
import com.example.delvin.dto.response.LicenseProductResponse;
import com.example.delvin.dto.response.PriceLicenseKeyResponse;
import com.example.delvin.entity.LicenseKey;
import com.example.delvin.enums.KeyStatus;
import com.example.delvin.enums.LicenseKeyPrefix;
import com.example.delvin.repository.LicenseKeyRepository;
import com.example.delvin.service.LicenseKeyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/license-keys")
@AllArgsConstructor
public class LicenseKeyController {
    private final LicenseKeyService licenseKeyService;
    private final LicenseKeyRepository licenseKeyRepository;

    @GetMapping
    public ApiResponse<List<LicenseKeyResponse>> getAllLicenseKeys() {
        List<LicenseKeyResponse> licenseKeys = licenseKeyService.getAllLicenseKeys();
        return ApiResponse.<List<LicenseKeyResponse>>builder()
                .result(licenseKeys)
                .message("Lấy danh sách license key thành công")
                .build();
    }

    @GetMapping("{id}")
    public ApiResponse<LicenseKeyResponse> getLicenseKeyById(@PathVariable Long id) {
        LicenseKeyResponse licenseKey = licenseKeyService.getLicenseKeyById(id);
        return ApiResponse.<LicenseKeyResponse>builder()
                .result(licenseKey)
                .message("Lấy license key thành công")
                .build();
    }

    @PostMapping("/create")
    public ApiResponse<LicenseKeyResponse> createLicenseKey(
            @RequestParam Long licenseProductId,
            @RequestParam LicenseKeyPrefix typePrefix,
            @RequestParam Long priceLicenseKeyId
    ) {
        LicenseKeyCreateRequest request =
                new LicenseKeyCreateRequest(licenseProductId,priceLicenseKeyId, typePrefix);
        LicenseKeyResponse response = licenseKeyService.createLicenseKey(request);


        return ApiResponse.<LicenseKeyResponse>builder()
                .result(response)
                .message("Tạo license key thành công")
                .build();
    }


    @PostMapping("/update-status")
    public ApiResponse<LicenseKeyResponse> updateLicenseKeyStatus(@RequestParam Long id, @RequestParam Integer status) {
        LicenseKeyResponse updatedLicenseKey = licenseKeyService.updateLicenseKeyStatus(id, switch (status) {
            case 1 -> KeyStatus.SOLD;
            case 2 -> KeyStatus.USED;
            case 3 -> KeyStatus.EXPIRED;
            default -> KeyStatus.AVAILABLE;
        });
        return ApiResponse.<LicenseKeyResponse>builder()
                .result(updatedLicenseKey)
                .message("Cập nhật trạng thái license key thành công")
                .build();
    }
}
