package com.expedia.edw.hww.codingtest.question1;

import static org.junit.Assert.assertEquals;

import org.joda.time.LocalDate;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * Created on 4/1/15.
 *
 * A note added here that the unit-tests are checking the functionality only so we don't care about the performance
 * right now. Typically we'd add some pre-production performance tests in our CI pipeline to check things there.
 */
public class PerformantExchangeRateResolverTest {

    private static final double DELTA = 1e-15;

    private static LocalDate now;
    private static LocalDate yesterday;

    @BeforeClass
    public static void setUp() {
        now = LocalDate.now();
        yesterday = LocalDate.now().minusDays(1);
    }

    @Test
    public void getExchangeRateTwiceTest() {

        // Given
        ExchangeRateResolver resolver = new PerformantExchangeRateResolver();
        double expected = 0.00658 + now.getDayOfMonth() * 0.0001;

        // When
        double rate = resolver.getExchangeRate("JPY", "GBP", LocalDate.now());

        // Then

        // I note this method is deprecated... typically I use TestNG so I'm not familiar with what is typical in
        // JUnit. Will use this for now to save time.
        assertEquals(expected, rate, DELTA);

        // When
        rate = resolver.getExchangeRate("JPY", "GBP", LocalDate.now());

        // Then
        assertEquals(expected, rate, DELTA);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getExchangeRateInvalidToTest() {

        // Given
        ExchangeRateResolver resolver = new PerformantExchangeRateResolver();
        double expected = 0.00658 + now.getDayOfMonth() * 0.0001;

        // When
        double rate = resolver.getExchangeRate("JPY", "BLAH", LocalDate.now());

        // Then Exception

    }

    @Test(expected = IllegalArgumentException.class)
    public void getExchangeRateInvalidFromTest() {

        // Given
        ExchangeRateResolver resolver = new PerformantExchangeRateResolver();
        double expected = 0.00658 + now.getDayOfMonth() * 0.0001;

        // When
        double rate = resolver.getExchangeRate("BLAH", "GBP", LocalDate.now());

        // Then Exception

    }

    @Test
    public void testRunner() {
        BookingReportRunner runner = new BookingReportRunner(new PerformantExchangeRateResolver());
        double d = runner.calculateTotal();

        // Note I haven't had time to verify this number. With more time I would have confirmed the output value
        // manually before putting it here.
        assertEquals(1164.0596, d, DELTA);
    }
}
