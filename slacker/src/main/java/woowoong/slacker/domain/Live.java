package woowoong.slacker.domain;

import jakarta.persistence.*;
import lombok.Getter;

import java.sql.Time;
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

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "club_id")
    private Club clubId;  // 공연장과의 외래키 관계

    private String genre;
    private int advancePrice;
    private int doorPrice;
    private String notice;
    private String timetable;
    private String image;
    private int remainNumOfSeats;
    private Time startTime;

    public Club getClubId() {
        return clubId;
    }

    public Live(Long id, String title, String bandLineup, LocalDate date, String genre, int advancePrice, int doorPrice, String notice, String timetable, String image, int remainNumOfSeats, Time startTime) {
        this.id = id;
        this.title = title;
        this.bandLineup = bandLineup;
        this.date = date;
        this.genre = genre;
        this.advancePrice = advancePrice;
        this.doorPrice = doorPrice;
        this.notice = notice;
        this.timetable = timetable;
        this.image = image;
        this.remainNumOfSeats = remainNumOfSeats;
        this.startTime = startTime;
    }

    public Live() {

    }

    public Live(String title, String bandLineup, LocalDate date, Long club_id, String genre, int advancePrice, int doorPrice, String notice, String timetable, String image, Time startTime) {
    }

    public void setId(Long id) {
        this.id = id;
    }
    public void setTitle(String title) {
        this.title = title;
    }
    public void setBandLineup(String bandLineup) {
        this.bandLineup = bandLineup;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public void setGenre(String genre) {
        this.genre = genre;
    }
    public void setAdvancePrice(int advancePrice) {
        this.advancePrice = advancePrice;
    }
    public void setDoorPrice(int doorPrice) {
        this.doorPrice = doorPrice;
    }
    public void setNotice(String notice) {
        this.notice = notice;
    }
    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }
    public void setImage(String image) {
        this.image = image;
    }
    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }
    public void setClubId(Club clubId) {
        this.clubId = clubId;
    }
    public void setRemainNumOfSeats(int remainNumOfSeats) {
        this.remainNumOfSeats = remainNumOfSeats;
    }
  
}
