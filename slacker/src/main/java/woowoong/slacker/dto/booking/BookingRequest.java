package woowoong.slacker.dto.booking;

import lombok.*;

@Getter
@Builder
public class BookingRequest {
    private String userEmail;
    private String liveId;
    private int numberOfTickets;
}
