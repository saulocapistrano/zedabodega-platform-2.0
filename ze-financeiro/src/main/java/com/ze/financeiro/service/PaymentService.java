package com.ze.financeiro.service;

import com.ze.financeiro.dto.request.PaymentRequestDTO;
import com.ze.financeiro.dto.response.PaymentResponseDTO;
import com.ze.financeiro.domain.model.Payment;
import com.ze.financeiro.domain.PaymentMethod;
import com.ze.financeiro.domain.vo.Money;
import com.ze.financeiro.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final PaymentRepository paymentRepository;

    @Transactional
    public PaymentResponseDTO create(PaymentRequestDTO dto) {
        Payment payment = Payment.createNew(
                dto.orderId(),
                new Money(dto.amount()),
                PaymentMethod.valueOf(dto.method())
        );

        Payment saved = paymentRepository.save(payment);

        return PaymentResponseDTO.from(saved);
    }

    public PaymentResponseDTO findById(UUID id) {
        Payment payment = getPaymentOrThrow(id);
        return PaymentResponseDTO.from(payment);
    }

    public List<PaymentResponseDTO> findAll() {
        return paymentRepository.findAll()
                .stream()
                .map(PaymentResponseDTO::from)
                .collect(Collectors.toList());
    }

    @Transactional
    public PaymentResponseDTO update(UUID id, PaymentRequestDTO dto) {
        Payment payment = getPaymentOrThrow(id);

        payment.updateBasicData(
                dto.orderId(),
                new Money(dto.amount()),
                PaymentMethod.fromCode(dto.method())
        );

        return PaymentResponseDTO.from(payment);
    }

    @Transactional
    public void delete(UUID id) {
        if (!paymentRepository.existsById(id)) {
            throw new EntityNotFoundException("Payment not found with ID: " + id);
        }
        paymentRepository.deleteById(id);
    }

    private Payment getPaymentOrThrow(UUID id) {
        return paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));
    }
}
