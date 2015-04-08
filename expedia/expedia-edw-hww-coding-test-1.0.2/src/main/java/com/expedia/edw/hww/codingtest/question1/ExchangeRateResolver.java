package com.expedia.edw.hww.codingtest.question1;

import java.util.Currency;

import org.joda.time.LocalDate;

/**
 * Provides the exchange rate between two {@link Currency currencies} as it was on a given {@link LocalDate}.
 */
interface ExchangeRateResolver {

  /**
   * Fetches the exchange rate as it was on a given day, to convert between a source and destination currency.
   * 
   * @param from The currency to fetch the exchange rate for.
   * @param localDate The day of the rate to use.
   * @return The exchange rate for that day.
   */
  double getExchangeRate(String from, String to, LocalDate localDate);

}
