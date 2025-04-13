package com.ze.financeiro.dto.response;

import com.ze.financeiro.domain.model.Payment;
import com.ze.financeiro.domain.PaymentMethod;
import com.ze.financeiro.domain.PaymentStatus;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentResponseDTO(
        UUID id,
        UUID orderId,
        BigDecimal amount,
        PaymentMethod method,
        PaymentStatus status,
        Instant createdAt,
        Instant approvedAt,
        String rejectedReason
) {

    public static PaymentResponseDTO from(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getOrderId(),
                payment.getAmount().getValue(),
                payment.getMethod(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getApprovedAt(),
                payment.getRejectedReason()
        );
    }
}
