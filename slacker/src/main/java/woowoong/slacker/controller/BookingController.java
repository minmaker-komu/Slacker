package woowoong.slacker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowoong.slacker.domain.Booking;
import woowoong.slacker.dto.BookingDto;
import woowoong.slacker.service.BookingService;

import java.util.List;

@RestController
@RequestMapping("/bookings")
public class BookingController {

    private final BookingService bookingService;

    @Autowired
    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    // 특정 유저의 예매 내역 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<List<BookingDto>> getUserBookings(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getBookingsByUserId(userId);
        List<BookingDto> bookingDtos = bookingService.liveToDtoList(bookings);
        return ResponseEntity.ok(bookingDtos);
    }

}
