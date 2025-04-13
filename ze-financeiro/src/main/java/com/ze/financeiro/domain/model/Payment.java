package com.ze.financeiro.domain.model;

import com.ze.financeiro.domain.PaymentMethod;
import com.ze.financeiro.domain.PaymentStatus;
import com.ze.financeiro.domain.vo.Money;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "payments")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Payment {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(nullable = false)
    private UUID orderId;

    @Embedded
    @AttributeOverride(name = "value", column = @Column(name = "amount", nullable = false, precision = 10, scale = 2))
    private Money amount;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentMethod method;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PaymentStatus status;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    private Instant approvedAt;

    private String rejectedReason;

    public static Payment createNew(UUID orderId, BigDecimal amount, String methodCode) {
        return Payment.builder()
                .orderId(orderId)
                .amount(new Money(amount))
                .method(PaymentMethod.fromCode(methodCode))
                .status(PaymentStatus.APPROVED)
                .createdAt(Instant.now())
                .approvedAt(Instant.now())
                .build();
    }

    public void approve() {
        this.status = PaymentStatus.APPROVED;
        this.approvedAt = Instant.now();
        this.rejectedReason = null;
    }

    public void reject(String reason) {
        this.status = PaymentStatus.REJECTED;
        this.rejectedReason = reason;
        this.approvedAt = null;
    }

    public boolean isApproved() {
        return PaymentStatus.APPROVED.equals(this.status);
    }

    public boolean isRejected() {
        return PaymentStatus.REJECTED.equals(this.status);
    }
    public void updateBasicData(UUID orderId, Money amount, PaymentMethod method) {
        this.orderId = orderId;
        this.amount = amount;
        this.method = method;
    }

}
