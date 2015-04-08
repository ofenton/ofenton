package com.expedia.edw.hww.codingtest.question3;

import org.joda.time.DateTime;

class Booking {

  private final long bookingId;
  private final String hotelName;
  private final DateTime eventTime;

  Booking(long bookingId, String hotelName, DateTime eventTime) {
    this.bookingId = bookingId;
    this.hotelName = hotelName;
    this.eventTime = eventTime;
  }

  long getBookingId() {
    return bookingId;
  }

  String getHotelName() {
    return hotelName;
  }

  public DateTime getEventTime() {
    return eventTime;
  }

  @Override
  public String toString() {
    StringBuilder builder = new StringBuilder();
    builder.append("Booking [bookingId=");
    builder.append(bookingId);
    builder.append(", hotelName=");
    builder.append(hotelName);
    builder.append(", eventTime=");
    builder.append(eventTime);
    builder.append("]");
    return builder.toString();
  }

  @Override
  public int hashCode() {
    final int prime = 31;
    int result = 1;
    result = prime * result + (int) (bookingId ^ (bookingId >>> 32));
    result = prime * result + ((eventTime == null) ? 0 : eventTime.hashCode());
    result = prime * result + ((hotelName == null) ? 0 : hotelName.hashCode());
    return result;
  }

  @Override
  public boolean equals(Object otherObject) {
    if (this == otherObject) {
      return true;
    }
    if (otherObject == null) {
      return false;
    }
    if (getClass() != otherObject.getClass()) {
      return false;
    }
    Booking other = (Booking) otherObject;
    if (bookingId != other.bookingId) {
      return false;
    }
    if (eventTime == null) {
      if (other.eventTime != null) {
        return false;
      }
    } else if (!eventTime.equals(other.eventTime)) {
      return false;
    }
    if (hotelName == null) {
      if (other.hotelName != null) {
        return false;
      }
    } else if (!hotelName.equals(other.hotelName)) {
      return false;
    }
    return true;
  }

}
