package woowoong.slacker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowoong.slacker.domain.Booking;
import woowoong.slacker.dto.booking.BookingRequest;
import woowoong.slacker.dto.booking.BookingResponse;
import woowoong.slacker.dto.booking.UpdateBookingStatusRequest;
import woowoong.slacker.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/booking")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @PostMapping("/create")
    public ResponseEntity<BookingResponse> createOrder(@RequestBody BookingRequest request) {
        BookingResponse bookingResponse = bookingService.createBooking(request);
        return ResponseEntity.ok(bookingResponse);
    }

    @PostMapping("/update-status")
    public ResponseEntity<BookingResponse> updateOrderStatus(@RequestBody UpdateBookingStatusRequest request) {
        BookingResponse bookingResponse = bookingService.updateOrderStatus(request);
        return ResponseEntity.ok(bookingResponse);
    }

    // 특정 유저의 예매 내역 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingResponse>> getUserBookings(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        List<BookingResponse> bookingResponseList = bookingService.liveToDtoList(bookings);
        return ResponseEntity.ok(bookingResponseList);
    }

}
