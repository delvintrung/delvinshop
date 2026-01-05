package com.example.delvin.entity;

import com.example.delvin.enums.KeyStatus;
import com.example.delvin.enums.LicenseKeyPrefix;
import com.example.delvin.enums.LicenseKeyProductStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;

@Entity
@Table(name = "license_products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LicenseProduct {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    String name;
    String description;
    @Enumerated(EnumType.STRING)
    private LicenseKeyPrefix useWith;
    @Enumerated(EnumType.STRING)
    LicenseKeyProductStatus status;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "license_type_id", nullable = false)
    LicenseType licenseType;
    @Column(nullable = false, updatable = false)
    Instant createdAt;
    @Column(nullable = false)
    Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        Instant now = Instant.now();
        this.status = LicenseKeyProductStatus.ACTIVE;
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
        }
}
