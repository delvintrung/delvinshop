package com.example.delvin.controller;


import com.example.delvin.config.apiconfig.ApiResponse;
import com.example.delvin.dto.request.LicenseTypeRequest;
import com.example.delvin.entity.LicenseType;
import com.example.delvin.service.LicenseTypeService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/license-types")
@RequiredArgsConstructor
public class LicenseTypeController {
    private final LicenseTypeService licenseTypeService;

    @GetMapping
    public ApiResponse<List<LicenseType>> getAll() {
        return ApiResponse.<List<LicenseType>>builder()
                .result(licenseTypeService.getAllLicenseTypes())
                .message("Lấy danh sách loại license thành công")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<LicenseType> getById(@PathVariable Long id) {
        LicenseType result = licenseTypeService.getLicenseTypeById(id);
        return ApiResponse.<LicenseType>builder()
                .result(result)
                .message("Lấy loại license thành công")
                .build();
    }

    @PostMapping
    public ApiResponse<LicenseType> create(@RequestBody LicenseTypeRequest request) {
        LicenseType result = licenseTypeService.createLicenseType(request);
        return ApiResponse.<LicenseType>builder()
                .result(result)
                .message("Tạo loại license thành công")
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<LicenseType> update(@PathVariable Long id, @RequestBody LicenseTypeRequest request) {
        LicenseType result = licenseTypeService.updateLicenseType(id, request);
        return ApiResponse.<LicenseType>builder()
                .result(result)
                .message("Cập nhật loại license thành công")
                .build();
    }

}
