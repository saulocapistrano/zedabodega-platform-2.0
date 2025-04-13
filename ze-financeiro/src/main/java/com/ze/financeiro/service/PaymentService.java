package com.ze.financeiro.service;


import com.ze.financeiro.dto.request.PaymentRequestDTO;
import com.ze.financeiro.dto.response.PaymentResponseDTO;
import com.ze.financeiro.entity.Payment;
import com.ze.financeiro.entity.PaymentMethod;
import com.ze.financeiro.entity.PaymentStatus;
import com.ze.financeiro.repository.PaymentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentResponseDTO create(PaymentRequestDTO dto) {
        PaymentMethod method = PaymentMethod.fromCode(dto.method());

        Payment payment = Payment.builder()
                .orderId(dto.orderId())
                .amount(dto.amount())
                .method(method)
                .status(PaymentStatus.APPROVED)
                .createdAt(Instant.now())
                .approvedAt(Instant.now())
                .build();

        Payment saved = paymentRepository.save(payment);

        return new PaymentResponseDTO(
                saved.getId(),
                saved.getOrderId(),
                saved.getAmount(),
                saved.getMethod(),
                saved.getStatus(),
                saved.getCreatedAt(),
                saved.getApprovedAt(),
                saved.getRejectedReason()
        );
    }
}
