package com.example.delvin.entity;

import com.example.delvin.enums.KeyStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "license_keys")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class LicenseKey {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String keyCode;
    @Enumerated(EnumType.STRING)
    private KeyStatus status;
    private Instant soldAt;
    private Instant activatedAt;
    private Instant expireAt;
    @Column(nullable = false, updatable = false)
    private Instant createdAt;
    @Column(nullable = false)
    private Instant updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "license_product_id", nullable = false)
    private LicenseProduct licenseProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "price_license_key_id", nullable = false)
    private PriceLicenseKey priceLicenseKey;

    @OneToOne(mappedBy = "licenseKey", cascade = CascadeType.ALL)
    private GiftContent giftContent;

    @PrePersist
    protected void onCreate() {
        if (this.status == null) {
            this.status = KeyStatus.AVAILABLE;
        }
        Instant now = Instant.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
    }
}
