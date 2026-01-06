package com.example.delvin.service.impl;

import com.example.delvin.config.apiconfig.AppException;
import com.example.delvin.config.apiconfig.ErrorCode;
import com.example.delvin.dto.request.LicenseKeyCreateRequest;
import com.example.delvin.dto.response.LicenseKeyResponse;
import com.example.delvin.entity.LicenseKey;
import com.example.delvin.entity.LicenseProduct;
import com.example.delvin.enums.KeyStatus;
import com.example.delvin.mapper.LicenseKeyMapper;
import com.example.delvin.repository.LicenseKeyRepository;
import com.example.delvin.repository.LicenseProductRepository;
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
    private final LicenseKeyMapper licenseKeyMapper;

    @Override
    public List<LicenseKeyResponse> getAllLicenseKeys() {

        List<LicenseKey> licenseKeys = licenseKeyRepository.findAll();
        return licenseKeyMapper.toResponseList(licenseKeys);
    }

    @Override
    public LicenseKey getLicenseKeyById(Long id) {
        return licenseKeyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LICENSE_KEY_NOT_FOUND));
    }

    @Transactional
    public LicenseKeyResponse createLicenseKey(LicenseKeyCreateRequest request) {
        LicenseProduct product = licenseProductRepository.findById(request.getLicenseProductId())
                .orElseThrow(() -> new AppException(ErrorCode.LICENSE_PRODUCT_NOT_FOUND));



        String finalKeyCode = generateUniqueKeyCode(String.valueOf(request.getTypePrefix()));
        LicenseKey licenseKey = new LicenseKey();
        licenseKey.setKeyCode(finalKeyCode);
        licenseKey.setLicenseProduct(product);
        licenseKey.setStatus(KeyStatus.AVAILABLE);
        LicenseKey savedKey = licenseKeyRepository.save(licenseKey);
        return licenseKeyMapper.toResponse(savedKey);
    }

    @Transactional
    public LicenseKey updateLicenseKeyStatus(Long id, KeyStatus status) {
        LicenseKey licenseKey = licenseKeyRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.LICENSE_KEY_NOT_FOUND));
        licenseKey.setStatus(status);
        return licenseKeyRepository.save(licenseKey);
    }

    private String generateUniqueKeyCode(String prefix) {
        String keyCode;
        int maxRetry = 5; // Chỉ thử tối đa 5 lần để tránh treo vô hạn (dù xác suất trùng rất thấp)
        int attempt = 0;

        do {
            // Sinh ngẫu nhiên 16 ký tự (4 block 4 ký tự)
            String randomPart = KeyGeneratorUtils.generateRandomString(16);
            // Ghép Prefix: VIP-ABCD-EFGH-IJKL-MNOP
            keyCode = KeyGeneratorUtils.formatKey(prefix, randomPart);

            attempt++;
            if (attempt > maxRetry) {
                throw new RuntimeException("Không thể sinh mã key duy nhất sau " + maxRetry + " lần thử. Hãy thử lại.");
            }
        } while (licenseKeyRepository.existsByKeyCode(keyCode));

        return keyCode;
    }
}
