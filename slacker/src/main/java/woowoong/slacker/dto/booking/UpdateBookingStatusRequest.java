package woowoong.slacker.dto.booking;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import woowoong.slacker.domain.BookingStatus;

@Getter
@Setter
@NoArgsConstructor
public class UpdateBookingStatusRequest {
    private Long bookingId;
    private BookingStatus status;
}
