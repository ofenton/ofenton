package com.expedia.edw.hww.codingtest.question3;

import java.util.List;

interface BookingConsolidator {

  List<Booking> consolidateOrders(Iterable<Booking> bookingHits);

}
