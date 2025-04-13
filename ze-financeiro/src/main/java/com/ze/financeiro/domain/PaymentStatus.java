package com.ze.financeiro.domain;

import lombok.Getter;

@Getter
public enum PaymentStatus {
    PENDING("Waiting for approval"),
    APPROVED("Payment successfully approved"),
    REJECTED("Payment rejected"),
    REFUNDED("Payment refunded");

    private final String description;

    PaymentStatus(String description) {
        this.description = description;
    }
}
