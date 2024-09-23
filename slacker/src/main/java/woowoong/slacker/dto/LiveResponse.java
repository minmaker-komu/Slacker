package woowoong.slacker.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowoong.slacker.domain.Live;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LiveResponse {
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

    public LiveResponse(Live live) {
        this.id = live.getId();
        this.title = live.getTitle();
        this.bandLineup = live.getBandLineup();
        this.date = live.getDate();
        this.club_id = live.getClub_id();
        this.genre = live.getGenre();
        this.advancePrice = live.getAdvancePrice();;
        this.doorPrice = live.getDoorPrice();
        this.notice = live.getNotice();
        this.timetable = live.getTimetable();
        this.image = live.getImage();
        this.remainNumOfSeats = live.getRemainNumOfSeats();
    }
}
