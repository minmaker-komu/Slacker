package woowoong.slacker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woowoong.slacker.domain.Booking;
import woowoong.slacker.domain.BookingStatus;
import woowoong.slacker.domain.Live;
import woowoong.slacker.domain.User;
import woowoong.slacker.dto.booking.BookingRequest;
import woowoong.slacker.dto.booking.BookingResponse;
import woowoong.slacker.dto.booking.UpdateBookingStatusRequest;
import woowoong.slacker.exception.BookingNotFoundException;
import woowoong.slacker.exception.LiveNotFoundException;
import woowoong.slacker.exception.UserNotFoundException;
import woowoong.slacker.repository.BookingRepository;
import woowoong.slacker.repository.LiveRepository;
import woowoong.slacker.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class BookingService {

    private final BookingRepository bookingRepository;
    private final UserRepository userRepository;
    private final LiveRepository liveRepository;

    @Autowired
    public BookingService(BookingRepository bookingRepository, UserRepository userRepository, LiveRepository liveRepository) {
        this.bookingRepository = bookingRepository;
        this.userRepository = userRepository;
        this.liveRepository = liveRepository;
    }

    public BookingResponse liveToDto(Booking booking) {
        return new BookingResponse(
                booking.getId(),
                booking.getUser().getId(),
                booking.getUser().getKakaoId(),
                booking.getLive().getId(),
                booking.getLive().getTitle(),
                booking.getStatus(),
                booking.getBookingDate(),
                booking.getNumberOfTickets(),
                booking.getTotalAmount(),
                booking.getUser().getUsername()
        );
    }

    // 예매 생성
    public BookingResponse createBooking(BookingRequest bookingRequest) {

        Long userId = bookingRequest.getUserId();
        String liveId = bookingRequest.getLiveId();
        int numberOfTickets = bookingRequest.getNumberOfTickets();

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException("User not found with kakaoId: " + userId));

        Live live = liveRepository.findById(Long.valueOf(liveId))
                .orElseThrow(() -> new LiveNotFoundException("Live not found with ID: " + liveId));

        LocalDateTime now = LocalDateTime.now();

        Booking booking = new Booking(user, live, BookingStatus.PENDING, now, numberOfTickets);
        Booking savedBooking = bookingRepository.save(booking);

        // 주문 저장
        return new BookingResponse(savedBooking);
    }

    // 주문 상태 업데이트
    public BookingResponse updateOrderStatus(UpdateBookingStatusRequest request) {

        Booking booking = bookingRepository.findById(request.getBookingId())
                .orElseThrow(() -> new BookingNotFoundException("Booking not found with ID: " + request.getBookingId()));

        booking.setStatus(request.getStatus());
        Booking updatedBooking = bookingRepository.save(booking);

        return new BookingResponse(updatedBooking);
    }

    // 예매 리스트를 DTO로 변환
    public List<BookingResponse> liveToDtoList(List<Booking> bookings) {
        return bookings.stream()
                .map(this::liveToDto)
                .collect(Collectors.toList());
    }

    // 특정 유저의 예매 내역
    public List<Booking> getBookingsByUserId(Long userId) {
        // 리포지토리에서 유저 ID로 예매 내역 조회
        return bookingRepository.findByUserId(userId);
    }
}
