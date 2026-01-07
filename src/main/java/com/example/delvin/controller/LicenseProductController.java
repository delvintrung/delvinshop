package com.example.delvin.controller;

import com.example.delvin.config.apiconfig.ApiResponse;
import com.example.delvin.dto.request.LicenseProductCreateRequest;
import com.example.delvin.dto.response.LicenseProductResponse;
import com.example.delvin.entity.LicenseProduct;
import com.example.delvin.service.impl.LicenseProductServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/license-products")
@AllArgsConstructor
public class LicenseProductController {
    LicenseProductServiceImpl licenseProductServiceImpl;
    @GetMapping
    public ApiResponse<List<LicenseProductResponse>> getAll() {
        List<LicenseProductResponse> licenseProducts = licenseProductServiceImpl.getAllLicenseProducts();
        return ApiResponse.<List<LicenseProductResponse>>builder()
                .result(licenseProducts)
                .message("Lấy danh sách sản phẩm license thành công")
                .build();
    }

    @PostMapping
    public ApiResponse<LicenseProductResponse> createLicenseProduct(@RequestBody LicenseProductCreateRequest licenseProduct) {
        LicenseProductResponse createdProduct = licenseProductServiceImpl.createLicenseProduct(licenseProduct);
        return ApiResponse.<LicenseProductResponse>builder()
                .result(createdProduct)
                .message("Tạo sản phẩm license thành công")
                .build();
    }

    @GetMapping("/{id}")
    public ApiResponse<LicenseProductResponse> getLicenseProductById(@RequestParam Long id) {
        LicenseProductResponse licenseProduct = licenseProductServiceImpl.getLicenseProductById(id);
        return ApiResponse.<LicenseProductResponse>builder()
                .result(licenseProduct)
                .message("Lấy sản phẩm license thành công")
                .build();
    }

    @PatchMapping("/{id}")
    public ApiResponse<LicenseProductResponse> updateLicenseProduct(@PathVariable Long id, @RequestBody LicenseProductCreateRequest licenseProductCreateRequest) {
        LicenseProductResponse licenseProduct =  licenseProductServiceImpl.updateLicenseProduct(id, licenseProductCreateRequest);
        return ApiResponse.<LicenseProductResponse>builder()
                .result(licenseProduct)
                .message("Cập nhật sản phẩm license thành công")
                .build();
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteLicenseProduct(@PathVariable Long id) {
        licenseProductServiceImpl.deleteLicenseProduct(id);
        return ApiResponse.<Void>builder()
                .message("Xóa sản phẩm license thành công")
                .build();
    }
}
