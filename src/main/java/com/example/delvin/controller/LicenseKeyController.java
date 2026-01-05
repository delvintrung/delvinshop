package com.example.delvin.controller;

import com.example.delvin.config.apiconfig.ApiResponse;
import com.example.delvin.dto.request.LicenseKeyCreateRequest;
import com.example.delvin.dto.response.LicenseKeyResponse;
import com.example.delvin.entity.LicenseKey;
import com.example.delvin.enums.KeyStatus;
import com.example.delvin.enums.LicenseKeyPrefix;
import com.example.delvin.repository.LicenseKeyRepository;
import com.example.delvin.service.LicenseKeyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/license-keys")
@AllArgsConstructor
public class LicenseKeyController {
    private final LicenseKeyService licenseKeyService;
    private final LicenseKeyRepository licenseKeyRepository;

    @PostMapping("/create")
    public ApiResponse<LicenseKeyResponse> createLicenseKey(
            @RequestParam Long licenseProductId,
            @RequestParam LicenseKeyPrefix typePrefix
    ) {
        LicenseKeyCreateRequest request =
                new LicenseKeyCreateRequest(licenseProductId, typePrefix);

        LicenseKey licenseKey =
                licenseKeyService.createLicenseKey(request);

        LicenseKeyResponse response = new LicenseKeyResponse(
                licenseKey.getId(),
                licenseKey.getKeyCode(),
                licenseKey.getStatus(),
                licenseKey.getLicenseProduct().getId(),
                licenseKey.getCreatedAt()
        );
        return ApiResponse.<LicenseKeyResponse>builder()
                .result(response)
                .message("Tạo license key thành công")
                .build();
    }


    @PostMapping("/update-status")
    public ApiResponse<LicenseKey> updateLicenseKeyStatus(@RequestParam Long id, @RequestParam Integer status) {
        LicenseKey updatedLicenseKey = licenseKeyService.updateLicenseKeyStatus(id, switch (status) {
            case 1 -> KeyStatus.SOLD;
            case 2 -> KeyStatus.USED;
            case 3 -> KeyStatus.EXPIRED;
            default -> KeyStatus.AVAILABLE;
        });
        return ApiResponse.<LicenseKey>builder()
                .result(updatedLicenseKey)
                .message("Cập nhật trạng thái license key thành công")
                .build();
    }
}
