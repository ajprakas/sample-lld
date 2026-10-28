package org.example.tennisBooking;

import java.util.*;

public class TennisBookingServiceWithDelayAndUsage {
    int maintenanceWindow;
    int usageLimit;
    Map<Integer, Integer> usageCount;

    public TennisBookingServiceWithDelayAndUsage(int maintenanceWindow, int usageLimit) {
        this.maintenanceWindow = maintenanceWindow;
        this.usageLimit = usageLimit;
        usageCount = new HashMap<>();
    }

    public List<TennisBookingService.BookingRecord> bookCourts(List<TennisBookingService.BookingRequest> bookingRequests) {
        bookingRequests.sort((a,b) -> a.start - b.start);
        PriorityQueue<TennisBookingService.BookingRecord> bookingsByendTime = new PriorityQueue<>((a, b) -> a.end- b.end);
        List<TennisBookingService.BookingRecord> bookingsReponse = new ArrayList<>();
        int courtNo=0;
        for(TennisBookingService.BookingRequest currBooking : bookingRequests) {
            if(currBooking.start<0 || currBooking.end<=0 || currBooking.start >= currBooking.end) {
                throw new IllegalArgumentException("Invalid Booking request");
            }
            if (!bookingsByendTime.isEmpty() &&
                    ((usageCount.get(bookingsByendTime.peek().courtNo) < usageLimit &&
                            bookingsByendTime.peek().end <= currBooking.start) ||
                            (usageCount.get(bookingsByendTime.peek().courtNo) == usageLimit &&
                                    (bookingsByendTime.peek().end + maintenanceWindow <= currBooking.start)))) {
                TennisBookingService.BookingRecord bookingRecord = bookingsByendTime.poll();
                courtNo= bookingRecord.courtNo;
            } else {
                courtNo++;
            }
            TennisBookingService.BookingRecord bookingRecord = new TennisBookingService.BookingRecord(currBooking.start, currBooking.end, courtNo);
            bookingsByendTime.add(bookingRecord);
            bookingsReponse.add(bookingRecord);
            usageCount.put(courtNo, usageCount.getOrDefault(courtNo, 0) +1);
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
