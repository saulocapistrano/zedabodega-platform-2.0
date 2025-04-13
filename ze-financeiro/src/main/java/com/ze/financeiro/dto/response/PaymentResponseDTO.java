package com.ze.financeiro.dto.response;


import com.ze.financeiro.entity.PaymentMethod;
import com.ze.financeiro.entity.PaymentStatus;

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
) {}
