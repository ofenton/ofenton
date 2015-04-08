package com.expedia.edw.hww.codingtest.question1;

import java.util.HashMap;
import java.util.Map;

import org.joda.time.LocalDate;

/**
 *
 */
public class PerformantExchangeRateResolver implements ExchangeRateResolver {

    /**
     * Inner class to store the exchange key in our cache
     */
    private class ExchangeRate {
        private LocalDate localDate;
        private String from;
        private String to;

        ExchangeRate(LocalDate localDate, String from, String to) {
            this.localDate = localDate;
            this.from = from;
            this.to = to;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;

            ExchangeRate that = (ExchangeRate) o;

            if (!from.equals(that.from)) return false;
            if (!localDate.equals(that.localDate)) return false;
            if (!to.equals(that.to)) return false;

            return true;
        }

        @Override
        public int hashCode() {
            int result = localDate.hashCode();
            result = 31 * result + from.hashCode();
            result = 31 * result + to.hashCode();
            return result;
        }
    }

    private ExchangeRateResolver dbExchangeRateResolver;
    private Map<ExchangeRate, Double> rateCache = new HashMap<>();

    PerformantExchangeRateResolver() {
        dbExchangeRateResolver = new DatabaseExchangeRateResolver();
    }

  /**
   * Fetches the exchange rate as it was on a given day, to convert between a source and destination currency.
   * Uses a local cache to get better performance.
   * 
   * @param from The currency to fetch the exchange rate for.
   * @param localDate The day of the rate to use.
   * @return The exchange rate for that day.
   */
  @Override
  public double getExchangeRate(String from, String to, LocalDate localDate) {

      ExchangeRate thisRate = new ExchangeRate(localDate, from, to);
      if (rateCache.containsKey(thisRate)) {
          return rateCache.get(thisRate);
      } else {
          double rate = dbExchangeRateResolver.getExchangeRate(from, to, localDate);
          rateCache.put(thisRate, rate);
          return rate;
      }
  }

}
