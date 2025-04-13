package com.ze.financeiro.dto.mapper;

import com.ze.financeiro.domain.PaymentMethod;
import com.ze.financeiro.domain.model.Payment;
import com.ze.financeiro.domain.vo.Money;
import com.ze.financeiro.dto.request.PaymentRequestDTO;
import com.ze.financeiro.dto.response.PaymentResponseDTO;

public class PaymentMapper {

    public static Payment toEntity(PaymentRequestDTO dto) {
        return Payment.builder()
                .orderId(dto.orderId())
                .amount(Money.of(dto.amount()))
                .method(PaymentMethod.fromCode(dto.method()))
                .status(null)
                .build();
    }

    public static PaymentResponseDTO toDTO(Payment entity) {
        return new PaymentResponseDTO(
                entity.getId(),
                entity.getOrderId(),
                entity.getAmount().getValue(),
                entity.getMethod(),
                entity.getStatus(),
                entity.getCreatedAt(),
                entity.getApprovedAt(),
                entity.getRejectedReason()
        );
    }

    public static void update(Payment payment, PaymentRequestDTO dto) {
        payment.updateBasicData(
                dto.orderId(),
                Money.of(dto.amount()),
                PaymentMethod.fromCode(dto.method())
        );
    }
}
