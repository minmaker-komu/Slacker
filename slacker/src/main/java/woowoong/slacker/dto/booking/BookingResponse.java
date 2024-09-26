package woowoong.slacker.dto.booking;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import woowoong.slacker.domain.Booking;
import woowoong.slacker.domain.BookingStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BookingResponse {

    private Long id;  // 예매 ID
    private Long userId;  // 유저 ID
    private Long kakaoId;
    private Long liveId;  // 공연 ID
    private String liveTitle;  // 공연 제목
    private BookingStatus status;
    private LocalDateTime bookingDate;  // 예매한 날짜
    private int numberOfTickets;  // 예매한 티켓 수
    private int totalAmount; // 예매 가격
    private String tid; // 고유 결제 번호

    // 유저 정보 (필요한 경우)
    private String userName;  // 유저 이름 또는 닉네임

    public BookingResponse(Booking booking) {
        this.id = booking.getId();
        this.userId = booking.getUser().getId();
        this.kakaoId = booking.getUser().getKakaoId();
        this.liveId = booking.getLive().getId();
        this.liveTitle = booking.getLive().getTitle();
        this.status = booking.getStatus();
        this.bookingDate = booking.getBookingDate();
        this.numberOfTickets = booking.getNumberOfTickets();
        this.totalAmount = booking.getTotalAmount();
        this.tid = booking.getTid();
        this.userName = booking.getUser().getUsername();
    }
}
