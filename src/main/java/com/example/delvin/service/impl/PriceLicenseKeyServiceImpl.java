package com.example.delvin.service.impl;

import com.example.delvin.config.apiconfig.AppException;
import com.example.delvin.config.apiconfig.ErrorCode;
import com.example.delvin.dto.request.PriceLicenseKeyCreateRequest;
import com.example.delvin.entity.PriceLicenseKey;
import com.example.delvin.repository.PriceLicenseKeyRepository;
import com.example.delvin.service.PriceLicenseKeyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Service
@AllArgsConstructor
public class PriceLicenseKeyServiceImpl implements PriceLicenseKeyService {
    private final PriceLicenseKeyRepository priceLicenseKeyRepository;
    public List<PriceLicenseKey> getAllPriceLicenseKeys() {
        return priceLicenseKeyRepository.findAll();
    }
    public PriceLicenseKey getPriceLicenseKeyById(Long id) {
        return priceLicenseKeyRepository.findById(id).orElse(null);
    }

    public PriceLicenseKey createPriceLicenseKey(PriceLicenseKeyCreateRequest request) {
        PriceLicenseKey priceLicenseKey = new PriceLicenseKey();
        priceLicenseKey.setDescription(request.getDescription());
        priceLicenseKey.setPrice(request.getPrice());

        return priceLicenseKeyRepository.save(priceLicenseKey);
    }

    public PriceLicenseKey updatePriceLicenseKey(Long id,PriceLicenseKeyCreateRequest request) {
        PriceLicenseKey existingPriceLicenseKey = priceLicenseKeyRepository.findById(id).orElseThrow(() -> new AppException(ErrorCode.LICENSE_KEY_NOT_FOUND));

        if (request.getDescription() != null) {
            existingPriceLicenseKey.setDescription(request.getDescription());
        }
        if (request.getPrice() != null)
            existingPriceLicenseKey.setPrice(request.getPrice());
        return priceLicenseKeyRepository.save(existingPriceLicenseKey);
    }

    public void deletePriceLicenseKey(Long id) {
        priceLicenseKeyRepository.deleteById(id);
    }
}
