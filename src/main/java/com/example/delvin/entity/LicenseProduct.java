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
    Double price;
    Instant expireAt;
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
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
        }
}
