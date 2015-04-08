package com.expedia.edw.hww.codingtest.question1;

import org.joda.time.LocalDate;

/**
 * Represents a small subsection of customer hotel booking data.
 */
class HotelBooking {

  private final String hotelName;
  private final String customerCurrency;
  private final double orderPrice;
  private final LocalDate orderDate;

  HotelBooking(LocalDate orderDate, String hotelName, String customerCurrency, double orderPrice) {
    this.orderDate = orderDate;
    this.hotelName = hotelName;
    this.customerCurrency = customerCurrency;
    this.orderPrice = orderPrice;
  }

  String getHotelName() {
    return hotelName;
  }

  String getCustomerCurrency() {
    return customerCurrency;
  }

  double getOrderPrice() {
    return orderPrice;
  }

  LocalDate getOrderDate() {
    return orderDate;
  }

}
