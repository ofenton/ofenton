package com.expedia.edw.hww.codingtest.question3;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.junit.BeforeClass;
import org.junit.Test;

public class DuplicateBookingConsolidatorTest {

    private static DateTime now;
    private static DateTime earlier;

    @BeforeClass
    public static void setUp() {
        now = DateTime.now();
        earlier = DateTime.now().minusDays(1);
    }

    /* TESTS FOR THE consolidateOrders METHOD */
    @Test
    public void consolidateOrdersTest() {
        // Given
        Booking b1 = new Booking(14L, "hotelName", earlier);
        Booking b2 = new Booking(14L, "hotelName", now);
        Booking b3 = new Booking(15L, "another hotelName", earlier);

        // Any collection that's iterable
        List<Booking> bookingHits = new ArrayList<>();
        bookingHits.add(b1);
        bookingHits.add(b2);
        bookingHits.add(b3);

        // When
        BookingConsolidator dbc = new DuplicateBookingConsolidator();
        List<Booking> bookings = dbc.consolidateOrders(bookingHits);

        // Then
        assertEquals(2, bookings.size());

        // and a few checks on the actual data (noted we're enforcing order here & we might not want to do that)
        assertEquals(14L, bookings.get(0).getBookingId());
        assertEquals(earlier, bookings.get(0).getEventTime());
        assertEquals("hotelName", bookings.get(0).getHotelName());
        assertEquals(15L, bookings.get(1).getBookingId());
        assertEquals("another hotelName", bookings.get(1).getHotelName());
    }

    /* TESTS FOR THE getConsolidatedBooking METHOD */

    @Test
    public void getConsolidatedBookingDateTimeTest() throws ConsolidateBookingException {

        // Given
        Booking b1 = new Booking(14L, "hotelName", earlier);
        Booking b2 = new Booking(14L, "hotelName", now);

        // When
        Booking out = DuplicateBookingConsolidator.getConsolidatedBooking(b1, b2);


        // Then
        assertEquals(earlier, out.getEventTime());

        // Confirm these other things as well.
        assertEquals("hotelName", out.getHotelName());
        assertEquals(14L, out.getBookingId());
    }

    @Test
    public void getConsolidatedBookingHotelNameTest() throws ConsolidateBookingException {

        // Given
        Booking b1 = new Booking(14L, "hotel1", earlier);
        Booking b2 = new Booking(14L, "hotel2", now);

        // When
        Booking out = DuplicateBookingConsolidator.getConsolidatedBooking(b1, b2);

        // Then
        assertEquals("hotel2", out.getHotelName());
    }

    @Test(expected = ConsolidateBookingException.class)
    public void getConsolidatedBookingBadIdsTest() throws ConsolidateBookingException {

        // Given
        Booking b1 = new Booking(14L, "hotel1", earlier);
        Booking b2 = new Booking(15L, "hotel2", now);

        // When
        Booking out = DuplicateBookingConsolidator.getConsolidatedBooking(b1, b2);


        // Then throw Exception
    }


    /*
     * Further tests we could add if we had time
     *
     * Check for null values (esp. in the consolidation method as it will fail at present)
     * More here...
     */
}
