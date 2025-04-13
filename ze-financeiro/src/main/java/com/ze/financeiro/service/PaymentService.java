package com.ze.financeiro.service;

import com.ze.financeiro.dto.mapper.PaymentMapper;
import com.ze.financeiro.dto.request.PaymentRequestDTO;
import com.ze.financeiro.dto.response.PaymentResponseDTO;
import com.ze.financeiro.entity.Payment;
import com.ze.financeiro.entity.PaymentStatus;
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
        Payment payment = PaymentMapper.toEntity(dto);
        Payment saved = paymentRepository.save(payment);
        return PaymentMapper.toDTO(saved);
    }

    public PaymentResponseDTO findById(UUID id) {
        Payment payment = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));
        return PaymentMapper.toDTO(payment);
    }

    public List<PaymentResponseDTO> findAll() {
        return paymentRepository.findAll()
                .stream()
                .map(PaymentMapper::toDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public PaymentResponseDTO update(UUID id, PaymentRequestDTO dto) {
        Payment existing = paymentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Payment not found with ID: " + id));

        PaymentMapper.update(existing, dto);
        Payment updated = paymentRepository.save(existing);
        return PaymentMapper.toDTO(updated);
    }

    @Transactional
    public void delete(UUID id) {
        if (!paymentRepository.existsById(id)) {
            throw new EntityNotFoundException("Payment not found with ID: " + id);
        }
        paymentRepository.deleteById(id);
    }
}
