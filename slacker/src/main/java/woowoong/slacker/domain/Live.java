package woowoong.slacker.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDateTime;

public class Live {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long idx;
    private String title;
    private String bandLineup;
    private LocalDateTime date;
    private String club_id;
    private String genre;
    private int advancePrice;
    private int doorPrice;
    private String notice;
    private String timetable;
    private String image;

    public Live(Long idx, String title, String bandLineup, LocalDateTime date, String club_id, String genre, int advancePrice, int doorPrice, String notice, String timetable, String image) {
        this.idx = idx;
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
    }
}
