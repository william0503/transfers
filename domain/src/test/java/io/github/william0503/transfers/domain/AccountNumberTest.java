package io.github.william0503.transfers.domain;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

class AccountNumberTest {

    @Test
    void acceptsValidNumber() {
        var account = new AccountNumber("0001", "12345-6");

        assertThat(account.branch()).isEqualTo("0001");
        assertThat(account).hasToString("0001/12345-6");
    }

    @ParameterizedTest
    @CsvSource({
            "001,   12345-6",
            "00012, 12345-6",
            "0001,  123456",
            "0001,  1234-5",
            "abcd,  12345-6"
    })
    void rejectsInvalidFormat(String branch, String number) {
        assertThatThrownBy(() -> new AccountNumber(branch, number))
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void recordsWithSameValuesAreEqual() {
        assertThat(new AccountNumber("0001", "12345-6"))
                .isEqualTo(new AccountNumber("0001", "12345-6"))
                .hasSameHashCodeAs(new AccountNumber("0001", "12345-6"));
    }
}