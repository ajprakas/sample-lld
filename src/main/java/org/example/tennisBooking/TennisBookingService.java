package org.example.tennisBooking;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class TennisBookingService {

    public List<BookingRecord> bookCourts(List<BookingRequest> bookingRequests) {
        bookingRequests.sort((a,b) -> a.start - b.start);
        PriorityQueue<BookingRecord> bookingsByendTime = new PriorityQueue<>((a,b) -> a.end- b.end);
        List<BookingRecord> bookingsReponse = new ArrayList<>();
        int countNo=0;
        for(BookingRequest currBooking : bookingRequests) {
            if(currBooking.start<0 || currBooking.end<=0 || currBooking.start >= currBooking.end) {
                throw new IllegalArgumentException("Invalid Booking request");
            }
            if(!bookingsByendTime.isEmpty() && bookingsByendTime.peek().end<=currBooking.start) {
                BookingRecord bookingRecord = bookingsByendTime.poll();
                countNo= bookingRecord.courtNo;
            } else {
                countNo++;
            }
            BookingRecord bookingRecord = new BookingRecord(currBooking.start, currBooking.end, countNo);
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
            BookingRecord that = (BookingRecord) o;
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
