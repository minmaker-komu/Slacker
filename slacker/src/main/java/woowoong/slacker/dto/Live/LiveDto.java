package woowoong.slacker.dto.Live;

import woowoong.slacker.domain.Club;

import java.sql.Time;
import java.time.LocalDate;

public class LiveDto {

    private Long id;
    private String title;
    private String bandLineup;
    private LocalDate date;
    private Long club_id;
    private String genre;
    private int advancePrice;
    private int doorPrice;
    private String notice;
    private String timetable;
    private String image;

    private Time startTime;

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

    public Long getClub_id() {
        return club_id;
    }

    public void setClub_id(Long club_id) {
        this.club_id = club_id;
    }
}
