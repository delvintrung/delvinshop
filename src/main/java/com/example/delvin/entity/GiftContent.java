package com.example.delvin.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "gift_contents")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GiftContent {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String senderName;
    private String recipientName;
    private String title;

    @Column(columnDefinition = "TEXT")
    private String message;
    private String themeColor;
    private String backgroundMusic;
    private String imageUrl;

    @OneToOne
    @JoinColumn(name = "license_key_id", referencedColumnName = "id")
    private LicenseKey licenseKey;
}
