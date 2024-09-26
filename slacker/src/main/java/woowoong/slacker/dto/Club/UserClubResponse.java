package woowoong.slacker.dto.Club;

import lombok.Getter;
import lombok.NoArgsConstructor;
import woowoong.slacker.domain.Club;

@Getter
@NoArgsConstructor
public class UserClubResponse {
    private Long id;
    private String clubName;
    private String location;
    private String phoneNumber;
    private String website;
    private String notice;

    public UserClubResponse(Club club) {
        this.id = club.getId();
        this.clubName = club.getClubName();
        this.location = club.getLocation();
        this.phoneNumber = club.getPhoneNumber();
        this.website = club.getWebsite();
        this.notice = club.getNotice();
    }
}
