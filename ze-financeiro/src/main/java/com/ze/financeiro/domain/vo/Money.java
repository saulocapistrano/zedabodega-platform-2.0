package com.ze.financeiro.domain.vo;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

@Embeddable
@Getter
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Money {

    @Column(name = "value")
    private BigDecimal value;

    public static Money of(BigDecimal amount) {
        return new Money(amount.setScale(2, RoundingMode.HALF_EVEN));
    }

    public boolean isPositive() {
        return value.compareTo(BigDecimal.ZERO) > 0;
    }

    public boolean isNegative() {
        return value.compareTo(BigDecimal.ZERO) < 0;
    }

    public boolean isZero() {
        return value.compareTo(BigDecimal.ZERO) == 0;
    }

    public Money add(Money other) {
        return new Money(this.value.add(other.value));
    }

    public Money subtract(Money other) {
        return new Money(this.value.subtract(other.value));
    }

    @Override
    public String toString() {
        return "R$ " + value;
    }
}
