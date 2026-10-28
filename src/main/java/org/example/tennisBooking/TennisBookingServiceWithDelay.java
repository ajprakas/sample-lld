package org.example.tennisBooking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class TennisBookingServiceWithDelay {
    int maintananceWindow;

    public TennisBookingServiceWithDelay(int maintananceWindow) {
        this.maintananceWindow = maintananceWindow;
    }

    public List<TennisBookingService.BookingRecord> bookCourts(List<TennisBookingService.BookingRequest> bookingRequests) {
        bookingRequests.sort((a,b) -> a.start - b.start);
        PriorityQueue<TennisBookingService.BookingRecord> bookingsByendTime = new PriorityQueue<>((a, b) -> a.end- b.end);
        List<TennisBookingService.BookingRecord> bookingsReponse = new ArrayList<>();
        int countNo=0;
        for(TennisBookingService.BookingRequest currBooking : bookingRequests) {
            if(currBooking.start<0 || currBooking.end<=0 || currBooking.start >= currBooking.end) {
                throw new IllegalArgumentException("Invalid Booking request");
            }
            if(!bookingsByendTime.isEmpty() && bookingsByendTime.peek().end + maintananceWindow <=currBooking.start) {
                TennisBookingService.BookingRecord bookingRecord = bookingsByendTime.poll();
                countNo= bookingRecord.courtNo;
            } else {
                countNo++;
            }
            TennisBookingService.BookingRecord bookingRecord = new TennisBookingService.BookingRecord(currBooking.start, currBooking.end, countNo);
            bookingsByendTime.add(bookingRecord);
            bookingsReponse.add(bookingRecord);
        }

        return bookingsReponse;

    }
    public static class BookingRequest {
        int start;
        int end;

        public BookingRequest(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    public static class BookingRecord {
        int start;
        int end;
        int courtNo;

        public BookingRecord(int start, int end, int courtNo) {
            this.start = start;
            this.end = end;
            this.courtNo = courtNo;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            TennisBookingService.BookingRecord that = (TennisBookingService.BookingRecord) o;
            return start == that.start && end == that.end && courtNo == that.courtNo;
        }

        @Override
        public int hashCode() {
            return Objects.hash(start, end, courtNo);
        }

        @Override
        public String toString() {
            return "BookingRecord{" +
                    "start=" + start +
                    ", end=" + end +
                    ", courtNo=" + courtNo +
                    '}';
        }
    }
}
