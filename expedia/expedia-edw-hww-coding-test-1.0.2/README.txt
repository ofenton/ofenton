Coding test instructions
========================

Before you start here are some general guidelines:

* The source code for all the questions are located under 'src/main/java', make your modifications here in the same package 
structure  (i.e. for Question 1 only add code under the 'com.expedia.edw.hww.codingtest.question1' package).
* If you write unit tests, create a 'src/test/java' folder and then the same package structure as the class being tested.
* The jar files in the 'lib' folder must be added to your classpath.
* You have 2 hours to answer all the questions. If you get stuck on a question don't spend too long on it - rather move
  on to the next one. We would prefer seeing at least an attempt made at all questions than a "perfect" answer to just
  one question.
* If anything is unclear feel free to ask questions on the IRC channel for clarification.
* Feel free to add comments outlining your thoughts or describing your solution in pseudo-code, especially if you don't
  have time to finish something as well as you would like.
* When you are finished zip up the whole folder and e-mail it back to us.

The three test questions are described detail below. Good luck!

Question 1
==========

We have provided a class 'BookingReportRunner' that sums up a total of booking amounts for a month. To do this it needs
to use an 'ExchangeRateResolver' which is an interface that provides exchange rates used to convert an amount in one
currency to an amount in another currency using a rate defined on a certain day. This data is stored in a database. We
have an implementation called 'DatabaseExchangeRateResolver' which performs the desired logic but is slow - we 'fake'
this slowness by using a Thread.sleep() to emulate waiting for a busy database. 
 
The BookingReportRunner will only be run at the end of a month. Exchange rates are set once per day and, once set, are
never changed. The users of the BookingReportRunner are complaining that it's taking too long to run reports. We would
like you to implement the 'PerformantExchangeRateResolver' class to improve the overall performance while still using 
DatabaseExchangeRateResolver to retrieve results from the database.

Question 2
==========

We have provided a simple MapReduce framework, an example 'Program' that uses this framework, and some sample data. The
program 'WordCountProgram' can be executed within the framework by executing the main method in the
'WordCountProgramRunner' class. This program is an implementation of the classic MapReduce word count example - reading
in lines of text from the sample data and providing a count of the number of times each word occurred. The sample data
provided uses the line number as the key, and the full text line as the value:

  "01", "and the flat downs mark englands south"
  "02", "coast running down to dramatic cornwall"
  "03", "london ...
      
When you execute the main method you should see a set of ordered word counts like this:

  Output: [(a, 3), (alongside, 1), (and, 9), (are, 1), (art, 3), (beaches, 1), (britain, 2), ...
  
Your task is to provide a new 'Program' implementation (in the skeleton 'DuplicateLineDetectorProgram' class) that detects 
duplicate lines in the sample data. Your program must run within the provided MapReduce framework. Given the sample 
data as input, the final output of your program should be only lines that appear in the sample data more than 
once along with the line numbers at which the occurrences can be found. Note that although the sample data is 
small, other input data could be very large, both in terms of the number of lines and the length of individual 
lines (which is why we're using MapReduce).

You should not modify any of the classes in the 'mapreduce' package.

Question 3
==========

We have a system that delivers Booking events to a class that implements 'BookingConsolidator'. Bookings are uniquely 
identified by a booking ID. The system sometimes emits multiple Booking events for the same Booking ID. The 
BookingConsolidator should consolidate these multiple events so that:

 1. The output contains only *one* Booking for each unique booking ID. 
 2. If there are multiple bookings for the same booking ID then the EventTime in the Booking that is output must 
    be the EventTime from the *first* booking.
 3. If there are multiple bookings for the same booking ID then the HotelName in the Booking that is output must 
    be the HotelName from the *second* booking.    

The Iterable of Booking objects will always be ordered by booking ID. Note that the input list might be very large. 

Implement the 'DuplicateBookingConsolidator' class to satisfy the above requirements. 
