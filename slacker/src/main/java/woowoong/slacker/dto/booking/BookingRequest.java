package woowoong.slacker.dto.booking;

import lombok.*;

@Getter
@Builder
public class BookingRequest {
    private Long userId;
    private String liveId;
    private int numberOfTickets;
}
