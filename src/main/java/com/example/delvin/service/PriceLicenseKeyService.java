package com.example.delvin.service;

import com.example.delvin.dto.request.PriceLicenseKeyCreateRequest;
import com.example.delvin.entity.PriceLicenseKey;

import java.util.List;

public interface PriceLicenseKeyService {
    List<PriceLicenseKey> getAllPriceLicenseKeys();
    PriceLicenseKey getPriceLicenseKeyById(Long id);
    PriceLicenseKey createPriceLicenseKey(PriceLicenseKeyCreateRequest request);
    PriceLicenseKey updatePriceLicenseKey(Long id,PriceLicenseKeyCreateRequest request);
    void deletePriceLicenseKey(Long id);
}
