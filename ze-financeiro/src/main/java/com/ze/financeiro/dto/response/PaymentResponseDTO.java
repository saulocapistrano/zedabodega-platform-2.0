package com.ze.financeiro.dto.response;

import com.ze.financeiro.domain.PaymentMethod;
import com.ze.financeiro.domain.PaymentStatus;
import com.ze.financeiro.domain.model.Payment;

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

    // Método de fábrica estático para facilitar o mapeamento do domínio para o DTO
    public static PaymentResponseDTO from(Payment payment) {
        return new PaymentResponseDTO(
                payment.getId(),
                payment.getOrderId(),
                payment.getAmount().getValue(), // Extraindo o valor do Value Object Money
                payment.getMethod(),
                payment.getStatus(),
                payment.getCreatedAt(),
                payment.getApprovedAt(),
                payment.getRejectedReason()
        );
    }
}
