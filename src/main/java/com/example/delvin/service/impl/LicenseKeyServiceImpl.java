package com.example.delvin.service.impl;

import com.example.delvin.config.apiconfig.AppException;
import com.example.delvin.config.apiconfig.ErrorCode;
import com.example.delvin.dto.request.LicenseKeyCreateRequest;
import com.example.delvin.dto.response.LicenseKeyResponse;
import com.example.delvin.entity.LicenseKey;
import com.example.delvin.entity.LicenseProduct;
import com.example.delvin.entity.PriceLicenseKey;
import com.example.delvin.enums.KeyStatus;
import com.example.delvin.mapper.LicenseKeyMapper;
import com.example.delvin.repository.LicenseKeyRepository;
import com.example.delvin.repository.LicenseProductRepository;
import com.example.delvin.repository.PriceLicenseKeyRepository;
import com.example.delvin.service.LicenseKeyService;
import com.example.delvin.util.KeyGeneratorUtils;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LicenseKeyServiceImpl implements LicenseKeyService {
    private final LicenseKeyRepository licenseKeyRepository;
    private final LicenseProductRepository licenseProductRepository;
    private final PriceLicenseKeyRepository priceLicenseKeyRepository;
    private final LicenseKeyMapper licenseKeyMapper;

    @Override
    public List<LicenseKeyResponse> getAllLicenseKeys() {

        List<LicenseKey> licenseKeys = licenseKeyRepository.findAll();
        return licenseKeyMapper.toResponseList(licenseKeys);
    }

    @Override
    public LicenseKeyResponse getLicenseKeyById(Long id) {
        LicenseKey licenseKey = licenseKeyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LICENSE_KEY_NOT_FOUND));
        return licenseKeyMapper.toResponse(licenseKey);
    }

    @Transactional
    public LicenseKeyResponse createLicenseKey(LicenseKeyCreateRequest request) {
        LicenseProduct product = licenseProductRepository.findById(request.getLicenseProductId())
                .orElseThrow(() -> new AppException(ErrorCode.LICENSE_PRODUCT_NOT_FOUND));

        PriceLicenseKey priceLicenseKey = priceLicenseKeyRepository.findById(request.getPriceLicenseKeyId())
                .orElseThrow(()-> new AppException(ErrorCode.PRICE_LICENSE_KEY_NOT_FOUND));



        String finalKeyCode = generateUniqueKeyCode(String.valueOf(request.getTypePrefix()));
        LicenseKey licenseKey = new LicenseKey();
        licenseKey.setKeyCode(finalKeyCode);
        licenseKey.setLicenseProduct(product);
        licenseKey.setStatus(KeyStatus.AVAILABLE);
        licenseKey.setPriceLicenseKey(priceLicenseKey);
        LicenseKey savedKey = licenseKeyRepository.save(licenseKey);
        return licenseKeyMapper.toResponse(savedKey);
    }

    @Transactional
    public LicenseKeyResponse updateLicenseKeyStatus(Long id, KeyStatus status) {
        LicenseKey licenseKey = licenseKeyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LICENSE_KEY_NOT_FOUND));
        licenseKey.setStatus(status);
        licenseKeyRepository.save(licenseKey);
        return licenseKeyMapper.toResponse(licenseKey);
    }

    private String generateUniqueKeyCode(String prefix) {
        String keyCode;
        int maxRetry = 5;
        int attempt = 0;

        do {
            String randomPart = KeyGeneratorUtils.generateRandomString(16);
            keyCode = KeyGeneratorUtils.formatKey(prefix, randomPart);

            attempt++;
            if (attempt > maxRetry) {
                throw new RuntimeException("Không thể sinh mã key duy nhất sau " + maxRetry + " lần thử. Hãy thử lại.");
            }
        } while (licenseKeyRepository.existsByKeyCode(keyCode));

        return keyCode;
    }
}
