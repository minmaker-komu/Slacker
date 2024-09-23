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
    private Club club_id;  // 공연장과의 외래키 관계
    private String genre;
    private int advancePrice;
    private int doorPrice;
    private String notice;
    private String timetable;
    private String image;
    private int remainNumOfSeats;
    private Time startTime;


    public Live(Long id, String title, String bandLineup, LocalDate date, String club_id, String genre, int advancePrice, int doorPrice, String notice, String timetable, String image, int remainNumOfSeats, Time startTime) {
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
        this.startTime = startTime;
    }

    public Live() {

    }

    public Live(String title, String bandLineup, LocalDate date, Long club_id, String genre, int advancePrice, int doorPrice, String notice, String timetable, String image, Time startTime) {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBandLineup() {
        return bandLineup;
    }

    public void setBandLineup(String bandLineup) {
        this.bandLineup = bandLineup;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public int getAdvancePrice() {
        return advancePrice;
    }

    public void setAdvancePrice(int advancePrice) {
        this.advancePrice = advancePrice;
    }

    public int getDoorPrice() {
        return doorPrice;
    }

    public void setDoorPrice(int doorPrice) {
        this.doorPrice = doorPrice;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getTimetable() {
        return timetable;
    }

    public void setTimetable(String timetable) {
        this.timetable = timetable;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Time getStartTime() {
        return startTime;
    }

    public void setStartTime(Time startTime) {
        this.startTime = startTime;
    }

    public Club getClub_id() {
        return club_id;
    }

    public void setClub_id(Club club_id) {
        this.club_id = club_id;
    }
  
    public void setRemainNumOfSeats(int remainNumOfSeats) {
        this.remainNumOfSeats = remainNumOfSeats;
    }
  
}
