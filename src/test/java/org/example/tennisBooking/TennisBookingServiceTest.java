package org.example.tennisBooking;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class TennisBookingServiceTest {
    TennisBookingService tennisBookingService = new TennisBookingService();
    TennisBookingServiceWithDelay tennisBookingServiceWithDelay = new TennisBookingServiceWithDelay(    1);
    TennisBookingServiceWithDelayAndUsage tennisBookingServiceWithDelayAndUsage = new TennisBookingServiceWithDelayAndUsage(1, 3);
    @Test(expected = IllegalArgumentException.class)
    public void testInvalidBookingRequests(){
        List<TennisBookingService.BookingRequest> bookingRequestList = new ArrayList<>();
//        bookingRequestList.add(new TennisBookingService.BookingRequest(-1,1));
//        bookingRequestList.add(new TennisBookingService.BookingRequest(0,10));
        bookingRequestList.add(new TennisBookingService.BookingRequest(-1,0));
        tennisBookingService.bookCourts(bookingRequestList);
    }

    @Test
    public void testEmptyBookingRequests(){
        List<TennisBookingService.BookingRequest> bookingRequestList = new ArrayList<>();
//        bookingRequestList.add(new TennisBookingService.BookingRequest(-1,1));
//        bookingRequestList.add(new TennisBookingService.BookingRequest(0,10));
//        bookingRequestList.add(new TennisBookingService.BookingRequest(-1,0));
        List<TennisBookingService.BookingRecord> bookings = tennisBookingService.bookCourts(bookingRequestList);
        Assert.assertEquals(bookings.size(), 0);
    }

    @Test
    public void testBookingRequests(){
        List<TennisBookingService.BookingRequest> bookingRequestList = new ArrayList<>();
        bookingRequestList.add(new TennisBookingService.BookingRequest(5,10));
        bookingRequestList.add(new TennisBookingService.BookingRequest(15,20));
        bookingRequestList.add(new TennisBookingService.BookingRequest(0,40));
        bookingRequestList.add(new TennisBookingService.BookingRequest(16,18));
        List<TennisBookingService.BookingRecord> bookings = tennisBookingService.bookCourts(bookingRequestList);
        Assert.assertEquals(bookings.size(), 4);
        TennisBookingService.BookingRecord bookingRecord1 = bookings.get(0);
        TennisBookingService.BookingRecord bookingRecord2 = bookings.get(1);
        TennisBookingService.BookingRecord bookingRecord3 = bookings.get(2);
        TennisBookingService.BookingRecord bookingRecord4 = bookings.get(3);
        System.out.println(bookingRecord1);
        System.out.println(bookingRecord2);
        System.out.println(bookingRecord3);
        System.out.println(bookingRecord4);
       // Assert.assertEquals( new TennisBookingService.BookingRecord(0,40,1));

    }


    // [0,40], [9,20],[5,9]

    @Test
    public void testBookingRequestsWithMaintenenceWindow(){
        List<TennisBookingService.BookingRequest> bookingRequestList = new ArrayList<>();
        bookingRequestList.add(new TennisBookingService.BookingRequest(9,20));
        bookingRequestList.add(new TennisBookingService.BookingRequest(5,9));
        bookingRequestList.add(new TennisBookingService.BookingRequest(0,40));
        List<TennisBookingService.BookingRecord> bookings = tennisBookingServiceWithDelay.bookCourts(bookingRequestList);
        Assert.assertEquals(bookings.size(), 3);
        TennisBookingService.BookingRecord bookingRecord1 = bookings.get(0);
        TennisBookingService.BookingRecord bookingRecord2 = bookings.get(1);
        TennisBookingService.BookingRecord bookingRecord3 = bookings.get(2);
        //TennisBookingService.BookingRecord bookingRecord4 = bookings.get(3);
        System.out.println(bookingRecord1);
        System.out.println(bookingRecord2);
        System.out.println(bookingRecord3);
     //   System.out.println(bookingRecord4);
        // Assert.assertEquals( new TennisBookingService.BookingRecord(0,40,1));

    }

    @Test
    public void testBookingRequestsWithMaintenenceWindowAndUsageLimit(){
        List<TennisBookingService.BookingRequest> bookingRequestList = new ArrayList<>();
        bookingRequestList.add(new TennisBookingService.BookingRequest(0,40));
        bookingRequestList.add(new TennisBookingService.BookingRequest(5,9));
        bookingRequestList.add(new TennisBookingService.BookingRequest(9,20));
        bookingRequestList.add(new TennisBookingService.BookingRequest(20,30));
        bookingRequestList.add(new TennisBookingService.BookingRequest(30,40));
        bookingRequestList.add(new TennisBookingService.BookingRequest(40,50));
        List<TennisBookingService.BookingRecord> bookings = tennisBookingServiceWithDelayAndUsage.bookCourts(bookingRequestList);
        Assert.assertEquals(bookings.size(), 6);
        TennisBookingService.BookingRecord bookingRecord1 = bookings.get(0);
        TennisBookingService.BookingRecord bookingRecord2 = bookings.get(1);
        TennisBookingService.BookingRecord bookingRecord3 = bookings.get(2);
        TennisBookingService.BookingRecord bookingRecord4 = bookings.get(3);
        TennisBookingService.BookingRecord bookingRecord5 = bookings.get(4);
        TennisBookingService.BookingRecord bookingRecord6 = bookings.get(5);
        //TennisBookingService.BookingRecord bookingRecord4 = bookings.get(3);
        System.out.println(bookingRecord1);
        System.out.println(bookingRecord2);
        System.out.println(bookingRecord3);
        System.out.println(bookingRecord4);
        System.out.println(bookingRecord5);
        System.out.println(bookingRecord6);
        //   System.out.println(bookingRecord4);
        // Assert.assertEquals( new TennisBookingService.BookingRecord(0,40,1));

    }




}
