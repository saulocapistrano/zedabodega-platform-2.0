package com.ze.financeiro.domain.model;


import com.ze.financeiro.domain.PaymentMethod;
import com.ze.financeiro.domain.PaymentStatus;
import com.ze.financeiro.domain.vo.Money;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PaymentTest {

    @Test
    void shouldApprovePayment() {
        Payment payment = Payment.createNew(
                UUID.randomUUID(),
                new Money(new BigDecimal("199.99")),
                PaymentMethod.PIX
        );

        payment.approve();

        assertEquals(PaymentStatus.APPROVED, payment.getStatus());
        assertNotNull(payment.getApprovedAt());
        assertNull(payment.getRejectedReason());
    }

    @Test
    void shouldRejectPaymentWithReason() {
        Payment payment = Payment.createNew(
                UUID.randomUUID(),
                new Money(new BigDecimal("499.99")),
                PaymentMethod.CREDIT_CARD
        );

        String reason = "Card expired";
        payment.reject(reason);

        assertEquals(PaymentStatus.REJECTED, payment.getStatus());
        assertEquals(reason, payment.getRejectedReason());
        assertNull(payment.getApprovedAt());
    }

    @Test
    void shouldUpdateBasicData() {
        Payment payment = Payment.createNew(
                UUID.randomUUID(),
                new Money(new BigDecimal("50.00")),
                PaymentMethod.DEBIT_CARD
        );

        UUID newOrderId = UUID.randomUUID();
        Money newAmount = new Money(new BigDecimal("75.00"));
        PaymentMethod newMethod = PaymentMethod.PIX;

        payment.updateBasicData(newOrderId, newAmount, newMethod);

        assertEquals(newOrderId, payment.getOrderId());
        assertEquals(newAmount, payment.getAmount());
        assertEquals(newMethod, payment.getMethod());
    }
}
