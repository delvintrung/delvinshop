package com.example.delvin.controller;

import com.example.delvin.dto.request.LicenseProductCreateRequest;
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
    public List<LicenseProduct> getLicenseProducts() {
        return licenseProductServiceImpl.getAllLicenseProducts();
    }

    @PostMapping
    public LicenseProduct createLicenseProduct(LicenseProductCreateRequest licenseProduct) {
        return licenseProductServiceImpl.createLicenseProduct(licenseProduct);
    }

    @GetMapping("/{id}")
    public LicenseProduct getLicenseProductById(@RequestParam Long id) {
        return licenseProductServiceImpl.getLicenseProductById(id);
    }
}
