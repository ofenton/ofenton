package com.expedia.edw.hww.codingtest.question3;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;

import com.google.common.annotations.VisibleForTesting;

/**
 *
 */
public class DuplicateBookingConsolidator implements BookingConsolidator {

  @Override
  public List<Booking> consolidateOrders(Iterable<Booking> bookingHits) {

      // Use a list to store the output bookings.
      //
      // As a note about choice here, there is no need to use a set as the input bookingHits is already ordered so we
      // won't have problems checking if the booking already exists.
      List<Booking> bookings = new ArrayList<>();

      for (Booking booking : bookingHits) {
        if (bookings.size() > 0 && (bookings.get(bookings.size() - 1)).getBookingId() == booking.getBookingId()) {

            try {

                Booking b = getConsolidatedBooking(bookings.get(bookings.size() - 1), booking);

            } catch (ConsolidateBookingException e) {

                /*
                 * This is Exception should be caught and managed here. In a business-solution, a good approach
                 * might be to send an alert to an external monitoring system, but for now I'll simply print to stderr.
                 * The reason for using try-catch here is because we don't want the whole booking system to fall-over
                 * if one booking is in Error.
                 */
                System.err.println("Error while consolidating bookings: " + e.getMessage());
            }


        } else {
            // id is unique so far so just add the booking
            bookings.add(booking);
        }
      }

      return bookings;
  }

    /**
     * Consolidate two bookings into one.
     *
     * NOTES: In general it's bad practise to reuse one of the existing booking objects after changing its settings,
     * so here we create a new consolidated Booking object
     *
     * @param firstBooking
     * @param secondBooking
     * @return the consolidated booking object
     */
    @VisibleForTesting
    static Booking getConsolidatedBooking(Booking firstBooking, Booking secondBooking) throws ConsolidateBookingException {
        if (firstBooking.getBookingId() != secondBooking.getBookingId()) {
            throw new ConsolidateBookingException(String.format("Bookings do not have matching ids: %s, %s", firstBooking.getBookingId(), secondBooking.getBookingId()));
        }

        DateTime eventTime = firstBooking.getEventTime();
        String hotelName = secondBooking.getHotelName();
        return new Booking(firstBooking.getBookingId(), hotelName, eventTime);
    }

}
