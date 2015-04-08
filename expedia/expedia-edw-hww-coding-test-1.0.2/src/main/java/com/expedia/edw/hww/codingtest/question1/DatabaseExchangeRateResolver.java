package com.expedia.edw.hww.codingtest.question1;

import org.joda.time.LocalDate;

/**
 * A naive {@link ExchangeRateResolver} implementation that simulates high latency. Can only convert to GBP from EUR,
 * USD, JPY.
 * <p/>
 * Candidates <u>should not</u> modify this class or let it influence their solutions.
 */
public class DatabaseExchangeRateResolver implements ExchangeRateResolver {

  /**
   * Fetches the exchange rate as it was on a given day, to convert between a source and destination currency.
   * 
   * @param from The currency to fetch the exchange rate for.
   * @param localDate The day of the rate to use.
   * @return The exchange rate for that day.
   */
  @Override
  public double getExchangeRate(String from, String to, LocalDate localDate) {
    return fetchRateFromDatabase(from, to, localDate);
  }

  private double fetchRateFromDatabase(String from, String to, LocalDate localDate) {
    // This method emulates fetching the exchange rate for a certain currency on a certain day from a database. Since we
    // don't actually have a database it does a wait to emulate a slow database and then returns a 'pretend' value. How
    // the values are calculated aren't important.
    //
    // Candidates should not modify this method or let it influence their solutions.

    try {
      Thread.sleep(1000); // Simulate I/O wait from database
    } catch (InterruptedException ignored) {
    }

    // don't worry about how the below is calculated, we are just pretending to do what a database would do
    // to convert from a small set of input currencies to one output currency (GBP)
    if (!"GBP".equals(to)) {
      throw new IllegalArgumentException("Can only convert to GBP - please use that only.");
    }

    if ("JPY".equals(from)) {
      return 0.00658 + localDate.getDayOfMonth() * 0.0001;
    } else if ("EUR".equals(from)) {
      return 0.85290 + localDate.getDayOfMonth() * 0.02;
    } else if ("USD".equals(from)) {
      return 0.65575 + localDate.getDayOfMonth() * 0.01;
    } else {
      throw new IllegalArgumentException("Can only convert from EUR, USD, JPY - please use one of those.");
    }
  }
}
