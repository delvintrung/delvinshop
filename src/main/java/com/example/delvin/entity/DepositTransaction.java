package com.example.delvin.entity;


import com.example.delvin.enums.DepositTransactionStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "deposit_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositTransaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "wallet_id")
    private UserWallet wallet; // Nạp vào ví nào

    private Double amount;

    @Column(unique = true)
    private String bankTransactionId;

    private String description;

    private String bankAccount;

    private Instant transactionDate;
    private Instant processedAt;

    @Enumerated(EnumType.STRING)
    private DepositTransactionStatus status;

    @PrePersist
    protected void onCreate() {
        if (this.status == null) {
            this.status = DepositTransactionStatus.PENDING;
        }
        this.transactionDate = Instant.now();
    }
}
