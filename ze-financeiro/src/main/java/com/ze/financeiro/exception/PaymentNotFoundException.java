package com.ze.financeiro.exception;

public class PaymentNotFoundException extends RuntimeException {
    public PaymentNotFoundException(String id) {
        super("Payment not found with ID: " + id);
    }
}
