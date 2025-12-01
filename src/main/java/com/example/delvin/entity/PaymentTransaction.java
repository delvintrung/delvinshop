package com.example.delvin.entity;


import com.example.delvin.enums.PaymentMethod;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "payment_transactions")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentTransaction {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id")
    private Order order;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    private Double amount;

    private String transactionRef;

    // Mã giao dịch phía ngân hàng/ví trả về (VD: vnp_TransactionNo, momo transId)
    private String gatewayTransactionId;

    // Mã lỗi/trạng thái từ cổng thanh toán (VD: "00" là thành công ở VNPAY)
    private String responseCode;

    private String status; // SUCCESS, FAILED (Trạng thái của giao dịch này)

    @Column(columnDefinition = "TEXT")
    private String rawResponse; // Lưu toàn bộ JSON response để debug

    private LocalDateTime transactionTime; // Thời gian thực hiện
}