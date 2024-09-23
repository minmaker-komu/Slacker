package woowoong.slacker.dto.Live;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowoong.slacker.domain.Live;

import java.sql.Time;
import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class LiveResponse {
    private Long id;
    private String title;
    private String bandLineup;
    private LocalDate date;
    private Long clubId;
    private String genre;
    private int advancePrice;
    private int doorPrice;
    private String notice;
    private String timetable;
    private String image;
    private int remainNumOfSeats;
    private Time startTime;

    public LiveResponse(Live live) {
        this.id = live.getId();
        this.title = live.getTitle();
        this.bandLineup = live.getBandLineup();
        this.date = live.getDate();
        this.clubId = live.getClubId().getId();;
        this.genre = live.getGenre();
        this.advancePrice = live.getAdvancePrice();;
        this.doorPrice = live.getDoorPrice();
        this.notice = live.getNotice();
        this.timetable = live.getTimetable();
        this.image = live.getImage();
        this.remainNumOfSeats = live.getRemainNumOfSeats();
    }
}
