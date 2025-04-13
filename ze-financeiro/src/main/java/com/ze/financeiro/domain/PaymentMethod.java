package com.ze.financeiro.domain;

import lombok.Getter;

@Getter
public enum PaymentMethod {
    PIX("PIX", "Pix transfer", true),
    BOLETO("BOLETO", "Boleto bancário", false),
    CREDIT_CARD("CREDIT_CARD", "Credit card", true),
    DEBIT_CARD("DEBIT_CARD", "Debit card", true),
    CASH("CASH", "Cash payment", false);

    private final String code;
    private final String description;
    private final boolean immediate;

    PaymentMethod(String code, String description, boolean immediate) {
        this.code = code;
        this.description = description;
        this.immediate = immediate;
    }

    public static PaymentMethod fromCode(String code) {
        for (PaymentMethod method : values()) {
            if (method.code.equalsIgnoreCase(code)) {
                return method;
            }
        }
        throw new IllegalArgumentException("Unknown payment method: " + code);
    }
}

