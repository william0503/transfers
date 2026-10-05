package io.github.william0503.transfers.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class MoneyTest {
    @Test
    @DisplayName("10, 10.0 and 10.00 are the same amount")
    void normalizeScale(){
        assertThat(Money.brl("10")).isEqualTo(Money.brl("10.00"));
        assertThat(Money.brl("10.0")).isEqualTo(Money.brl("10"));
    }

    @Test
    @DisplayName("10, 10.0 and 10.00 are the same amount")
    void rejectsNegativeAmount(){
        assertThatThrownBy(() -> Money.brl("-0.01"))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessageContaining("negative");
    }

    @ParameterizedTest
    @ValueSource(strings = {"0.001", "1.999", "10.123"})
    void rejectsMoreThanTwoDecimals(String amount) {
        assertThatThrownBy(() -> Money.brl(amount))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void addsAndSubtracts() {
        Money balance = Money.brl("100.00");

        assertThat(balance.plus(Money.brl("0.10"))).isEqualTo(Money.brl("100.10"));
        assertThat(balance.minus(Money.brl("99.99"))).isEqualTo(Money.brl("0.01"));
    }

    @Test
    void subtractingMoreThanAvailableFails() {
        assertThatThrownBy(() -> Money.brl("10").minus(Money.brl("10.01")))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void bigDecimalEqualsComparesScaleToo() {
        // This is why Money normalizes the scale in its constructor.
        assertThat(new BigDecimal("10.0").equals(new BigDecimal("10.00"))).isFalse();
        assertThat(new BigDecimal("10.0").compareTo(new BigDecimal("10.00"))).isZero();
    }
}
