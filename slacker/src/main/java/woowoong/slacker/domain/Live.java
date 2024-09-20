package woowoong.slacker.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Entity
@Getter
public class Live {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String bandLineup;
    private LocalDate date;
    private String club_id;
    private String genre;
    private int advancePrice;
    private int doorPrice;
    private String notice;
    private String timetable;
    private String image;
    private int remainNumOfSeats;

    public Live(Long id, String title, String bandLineup, LocalDate date, String club_id, String genre, int advancePrice, int doorPrice, String notice, String timetable, String image, int remainNumOfSeats) {
        this.id = id;
        this.title = title;
        this.bandLineup = bandLineup;
        this.date = date;
        this.club_id = club_id;
        this.genre = genre;
        this.advancePrice = advancePrice;
        this.doorPrice = doorPrice;
        this.notice = notice;
        this.timetable = timetable;
        this.image = image;
        this.remainNumOfSeats = remainNumOfSeats;
    }

    public Live() {

    }

    public void setRemainNumOfSeats(int remainNumOfSeats) {
        this.remainNumOfSeats = remainNumOfSeats;
    }
}
