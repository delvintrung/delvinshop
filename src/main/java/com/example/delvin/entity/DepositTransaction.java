package com.example.delvin.entity;


import com.example.delvin.enums.DepositTransactionStatus;
import com.example.delvin.enums.PaymentProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;

@Entity
@Table(name = "deposit_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DepositTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    private BigDecimal amount;
    private String currency;

    @Enumerated(EnumType.STRING)
    private PaymentProvider paymentProvider;

    private String orderCode;
    private String externalTxnId;

    @Enumerated(EnumType.STRING)
    private DepositTransactionStatus status;

    private String responseCode;
    private String responseMessage;

    private Instant paidAt;

    @Column(columnDefinition = "TEXT")
    private String requestPayload;

    @Column(columnDefinition = "TEXT")
    private String callbackPayload;

    private String secureHash;

    private Instant createdAt;
    private Instant updatedAt;

    @PrePersist
    protected void onCreate() {
        if (this.status == null) {
            this.status = DepositTransactionStatus.PENDING;
        }
        this.createdAt = Instant.now();
    }
    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = Instant.now();
        this.paidAt = Instant.now();
    }
}
