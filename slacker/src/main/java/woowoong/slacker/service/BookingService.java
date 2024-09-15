package woowoong.slacker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woowoong.slacker.domain.Booking;
import woowoong.slacker.dto.BookingDto;
import woowoong.slacker.repository.BookingRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository) {
        this.bookingRepository = bookingRepository;
    }

    public BookingDto convertToDto(Booking booking) {
        return new BookingDto(
                booking.getId(),
                booking.getUser().getId(),
                booking.getLive().getId(),
                booking.getLive().getTitle(),
                booking.getBookingDate(),
                booking.getNumberOfTickets(),
                booking.getUser().getUsername(),
                booking.getUser().getEmail()
        );
    }

    // 예매 리스트를 DTO로 변환
    public List<BookingDto> liveToDtoList(List<Booking> bookings) {
        return bookings.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    // 특정 유저의 예매 내역
    public List<Booking> getBookingsByUserId(Long userId) {
        // 리포지토리에서 유저 ID로 예매 내역 조회
        return bookingRepository.findByUserId(userId);

    }
}
