package com.eddarmitage.time;

import org.assertj.core.api.SoftAssertions;
import org.assertj.core.api.junit.jupiter.SoftAssertionsExtension;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.TimeUnit;

import static com.eddarmitage.time.FuzzyTimeCalculator.differenceBetween;

@ExtendWith(SoftAssertionsExtension.class)
class FuzzyTimeCalculatorTest {

    @ParameterizedTest
    @CsvFileSource(resources = "/differenceBetweenSamples.csv", numLinesToSkip = 1)
    void testDifferenceBetweenFunction(Instant from, Instant to, long expectedValue, ChronoUnit expectedUnit, SoftAssertions softly) {
        FuzzyTime result = differenceBetween(from, to);

        softly.assertThat(result.getQuantity()).isEqualTo(expectedValue);
        softly.assertThat(result.getUnit()).isEqualTo(expectedUnit);
    }
}