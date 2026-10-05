package io.github.william0503.transfers.domain;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SmokeTest {
    @Test
     void Test(){
        // Arrange
        var input = 1;

        // Act
        var result = input + 1;

        // Assert
        assertThat(result).isEqualTo(2);
    }
}
