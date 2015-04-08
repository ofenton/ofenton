package com.expedia.edw.hww.codingtest.question1;

import java.util.List;

import org.joda.time.LocalDate;

import com.google.common.collect.Lists;

/**
 * Runs a sample {@link HotelBooking} report.
 */
public class BookingReportRunner {

  private static String EUR = "EUR";
  private static String USD = "USD";
  private static String JPY = "JPY";
  private static String GBP = "GBP";

  private final ExchangeRateResolver exchangeRateResolver;

  BookingReportRunner(ExchangeRateResolver exchangeRateResolver) {
    this.exchangeRateResolver = exchangeRateResolver;
  }

  double calculateTotal() {
    List<HotelBooking> bookings = getMonthlyBookings();
    double totalBookingAmount = 0.0;
    for (HotelBooking booking : bookings) {
      double exchangeRate = exchangeRateResolver.getExchangeRate(booking.getCustomerCurrency(), GBP,
          booking.getOrderDate());
      totalBookingAmount += exchangeRate * booking.getOrderPrice();
    }
    return totalBookingAmount;
  }

  /**
   * Returns a List of bookings for a month. In the real world this would come out of some data store.
   * 
   * @return List of bookings for a month.
   */
  private static List<HotelBooking> getMonthlyBookings() {
    List<HotelBooking> bookings = Lists.newArrayList();
    bookings.add(new HotelBooking(new LocalDate(2013, 1, 2), "Park Lane Resort", EUR, 100.0d));
    bookings.add(new HotelBooking(new LocalDate(2013, 1, 2), "Oxford Metropole", USD, 200.0d));
    bookings.add(new HotelBooking(new LocalDate(2013, 1, 2), "Leeds continental", USD, 170.0d));
    bookings.add(new HotelBooking(new LocalDate(2013, 1, 2), "Covent Garden Rooms", USD, 80.0d));
    bookings.add(new HotelBooking(new LocalDate(2013, 1, 2), "Park Lane Resort", EUR, 120.0d));

    bookings.add(new HotelBooking(new LocalDate(2013, 1, 3), "Park Lane Resort", EUR, 250.0d));
    bookings.add(new HotelBooking(new LocalDate(2013, 1, 3), "Park Lane Resort", JPY, 15000.0d));
    bookings.add(new HotelBooking(new LocalDate(2013, 1, 3), "Covent Garden Rooms", JPY, 9000.0d));
    bookings.add(new HotelBooking(new LocalDate(2013, 1, 3), "Park Lane Resort", JPY, 15000.0d));

    bookings.add(new HotelBooking(new LocalDate(2013, 1, 4), "Park Lane Resort", EUR, 80.0d));
    bookings.add(new HotelBooking(new LocalDate(2013, 1, 4), "Oxford Metropole", EUR, 99.0d));
    return bookings;
  }

}
