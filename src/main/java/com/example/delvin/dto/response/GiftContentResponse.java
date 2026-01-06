package com.example.delvin.dto.response;

import com.example.delvin.enums.ThemeColor;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.Instant;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GiftContentResponse {
    private Long id;
    private String backgroundMusic;
    private String imageUrl;
    private String message;
    private String senderName;
    private String recipientName;
    private ThemeColor themeColor;
    private String title;
    private Instant createdAt;
    private Instant updatedAt;

    private LicenseKeyResponse licenseKey;
    private LicenseProductResponse licenseProduct;
}
