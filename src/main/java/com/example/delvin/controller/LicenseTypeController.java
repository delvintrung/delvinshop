package com.example.delvin.controller;


import com.example.delvin.config.apiconfig.ApiResponse;
import com.example.delvin.dto.request.LicenseTypeRequest;
import com.example.delvin.dto.response.LicenseTypeResponse;
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
    public ApiResponse<List<LicenseTypeResponse>> getAll() {
        return ApiResponse.<List<LicenseTypeResponse>>builder()
                .result(licenseTypeService.getAllLicenseTypes())
                .message("Lấy danh sách loại license thành công")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<LicenseTypeResponse> getById(@PathVariable Long id) {
        LicenseTypeResponse result = licenseTypeService.getLicenseTypeById(id);
        return ApiResponse.<LicenseTypeResponse>builder()
                .result(result)
                .message("Lấy loại license thành công")
                .build();
    }

    @PostMapping
    public ApiResponse<LicenseTypeResponse> create(@RequestBody LicenseTypeRequest request) {
        LicenseTypeResponse result = licenseTypeService.createLicenseType(request);
        return ApiResponse.<LicenseTypeResponse>builder()
                .result(result)
                .message("Tạo loại license thành công")
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<LicenseTypeResponse> update(@PathVariable Long id, @RequestBody LicenseTypeRequest request) {
        LicenseTypeResponse result = licenseTypeService.updateLicenseType(id, request);
        return ApiResponse.<LicenseTypeResponse>builder()
                .result(result)
                .message("Cập nhật loại license thành công")
                .build();
    }

}
