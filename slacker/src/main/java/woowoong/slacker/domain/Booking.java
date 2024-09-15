package woowoong.slacker.domain;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
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

    private LocalDateTime bookingDate;  // 예매한 날짜
    private int numberOfTickets;  // 예매한 티켓 수

    public Booking(Long id, User user, Live live, LocalDateTime bookingDate, int numberOfTickets) {
        this.id = id;
        this.user = user;
        this.live = live;
        this.bookingDate = bookingDate;
        this.numberOfTickets = numberOfTickets;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Live getLive() {
        return live;
    }

    public void setLive(Live live) {
        this.live = live;
    }

    public LocalDateTime getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(LocalDateTime bookingDate) {
        this.bookingDate = bookingDate;
    }

    public int getNumberOfTickets() {
        return numberOfTickets;
    }

    public void setNumberOfTickets(int numberOfTickets) {
        this.numberOfTickets = numberOfTickets;
    }
}

