package com.ze.financeiro.service;

import com.ze.financeiro.domain.PaymentStatus;
import com.ze.financeiro.dto.request.PaymentRequestDTO;
import com.ze.financeiro.dto.response.PaymentResponseDTO;
import com.ze.financeiro.domain.PaymentMethod;
import com.ze.financeiro.domain.model.Payment;
import com.ze.financeiro.domain.vo.Money;
import com.ze.financeiro.repository.PaymentRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private PaymentRepository paymentRepository;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private PaymentRequestDTO mockRequest() {
        return new PaymentRequestDTO(
                UUID.randomUUID(),
                new BigDecimal("100.00"),
                PaymentMethod.CREDIT_CARD.name()
        );
    }

    @Test
    void shouldCreatePaymentSuccessfully() {
        PaymentRequestDTO dto = mockRequest();

        Payment saved = Payment.createNew(
                dto.orderId(),
                new Money(dto.amount()),
                PaymentMethod.valueOf(dto.method())
        );

        when(paymentRepository.save(any(Payment.class))).thenReturn(saved);

        PaymentResponseDTO response = paymentService.create(dto);

        assertThat(response).isNotNull();
        assertThat(response.amount()).isEqualByComparingTo(dto.amount());
        assertThat(response.method()).isEqualTo(PaymentMethod.CREDIT_CARD);
        verify(paymentRepository).save(any(Payment.class));
    }

    @Test
    void shouldReturnPaymentById() {
        UUID id = UUID.randomUUID();

        Payment payment = Payment.builder()
                .id(id)
                .orderId(UUID.randomUUID())
                .amount(new Money(new BigDecimal("150.00")))
                .method(PaymentMethod.DEBIT_CARD)
                .status(PaymentStatus.APPROVED)
                .build();

        when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));

        PaymentResponseDTO response = paymentService.findById(id);

        assertThat(response.id()).isEqualTo(id);
        assertThat(response.method()).isEqualTo(PaymentMethod.DEBIT_CARD);
    }


    @Test
    void shouldThrowExceptionWhenNotFoundById() {
        UUID id = UUID.randomUUID();
        when(paymentRepository.findById(id)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> paymentService.findById(id))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Payment not found");
    }

    @Test
    void shouldUpdatePaymentSuccessfully() {
        UUID id = UUID.randomUUID();
        Payment payment = Payment.createNew(id, new Money(BigDecimal.TEN), PaymentMethod.PIX);
        PaymentRequestDTO dto = new PaymentRequestDTO(
                UUID.randomUUID(),
                new BigDecimal("200.00"),
                PaymentMethod.BOLETO.name()
        );

        when(paymentRepository.findById(id)).thenReturn(Optional.of(payment));

        PaymentResponseDTO updated = paymentService.update(id, dto);

        assertThat(updated.amount()).isEqualByComparingTo(dto.amount());
        assertThat(updated.method()).isEqualTo(PaymentMethod.BOLETO);
    }

    @Test
    void shouldDeletePaymentSuccessfully() {
        UUID id = UUID.randomUUID();
        when(paymentRepository.existsById(id)).thenReturn(true);

        paymentService.delete(id);

        verify(paymentRepository).deleteById(id);
    }

    @Test
    void shouldThrowExceptionWhenDeletingNonExistentPayment() {
        UUID id = UUID.randomUUID();
        when(paymentRepository.existsById(id)).thenReturn(false);

        assertThatThrownBy(() -> paymentService.delete(id))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void shouldReturnAllPayments() {
        Payment p1 = Payment.createNew(UUID.randomUUID(), new Money(BigDecimal.ONE), PaymentMethod.PIX);
        Payment p2 = Payment.createNew(UUID.randomUUID(), new Money(BigDecimal.TEN), PaymentMethod.CREDIT_CARD);

        when(paymentRepository.findAll()).thenReturn(List.of(p1, p2));

        List<PaymentResponseDTO> payments = paymentService.findAll();

        assertThat(payments).hasSize(2);
    }
}
