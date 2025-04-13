package com.ze.financeiro.dto.mapper;


import com.ze.financeiro.dto.request.PaymentRequestDTO;
import com.ze.financeiro.dto.response.PaymentResponseDTO;
import com.ze.financeiro.entity.Payment;
import com.ze.financeiro.entity.PaymentMethod;
import com.ze.financeiro.entity.PaymentStatus;

import java.time.Instant;

public class PaymentMapper {

    public static Payment toEntity(PaymentRequestDTO dto) {
        return Payment.builder()
                .orderId(dto.orderId())
                .amount(dto.amount())
                .method(PaymentMethod.fromCode(dto.method()))
                .status(PaymentStatus.APPROVED)
                .createdAt(Instant.now())
                .approvedAt(Instant.now())
                .build();
    }

    public static PaymentResponseDTO toDTO(Payment entity) {
        return new PaymentResponseDTO(
                entity.getId(),
                entity.getOrderId(),
                entity.getAmount(),
                entity.getMethod(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getApprovedAt(),
                entity.getRejectedReason()
        );

    }

    public static void update(Payment payment, PaymentRequestDTO dto) {
        payment.setOrderId(dto.orderId());
        payment.setAmount(dto.amount());
        payment.setMethod(PaymentMethod.fromCode(dto.method()));
    }

    public static void updateStatus(Payment payment, PaymentStatus newStatus, String rejectionReason) {
        payment.setStatus(newStatus);
        if (newStatus == PaymentStatus.APPROVED) {
            payment.setApprovedAt(Instant.now());
            payment.setRejectedReason(null);
        } else if (newStatus == PaymentStatus.REJECTED) {
            payment.setApprovedAt(null);
            payment.setRejectedReason(rejectionReason);
        }
    }
}
