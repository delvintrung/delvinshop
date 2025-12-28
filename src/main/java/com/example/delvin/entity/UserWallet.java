package com.example.delvin.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "user_wallets")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWallet {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(mappedBy = "wallet")
    private User user;

    private Double balance;

    private String currency;

    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        if (balance == null) balance = 0.0;
        if (currency == null) currency = "VND";
        updatedAt = Instant.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = Instant.now();
    }
}
