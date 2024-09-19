package woowoong.slacker.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Data
@Entity
@NoArgsConstructor
public class Booking {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;  // 예매한 유저

    @ManyToOne
    @JoinColumn(name = "live_id")
    private Live live;  // 예매한 공연

    @Enumerated(EnumType.STRING)
    private BookingStatus status;

    private LocalDateTime bookingDate;  // 예매한 날짜
    private int numberOfTickets;  // 예매한 티켓 수
    private int totalAmount; // 예매 총 가격

    public Booking(User user, Live live, BookingStatus status, LocalDateTime bookingDate, int numberOfTickets) {
        this.user = user;
        this.live = live;
        this.status = status;
        this.bookingDate = bookingDate;
        this.numberOfTickets = numberOfTickets;
        this.totalAmount = numberOfTickets * live.getAdvancePrice();
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setLive(Live live) {
        this.live = live;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
}

