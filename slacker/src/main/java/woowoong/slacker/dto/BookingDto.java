package woowoong.slacker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookingDto {

    private Long id;  // 예매 ID
    private Long userId;  // 유저 ID
    private Long liveId;  // 공연 ID
    private String liveTitle;  // 공연 제목
    private LocalDateTime bookingDate;  // 예매한 날짜
    private int numberOfTickets;  // 예매한 티켓 수

    // 유저 정보 (필요한 경우)
    private String userName;  // 유저 이름 또는 닉네임
    private String userEmail;  // 유저 이메일
}
