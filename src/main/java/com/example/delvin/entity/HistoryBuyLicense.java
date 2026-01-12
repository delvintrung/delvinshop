package com.example.delvin.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "history_buy_licenses")
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class HistoryBuyLicense {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "license_key_id", unique = true, nullable = false)
    private LicenseKey licenseKey;
    private Instant createdAt;
}
