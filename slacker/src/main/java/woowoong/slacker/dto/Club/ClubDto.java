package woowoong.slacker.dto.Club;

import lombok.Getter;

@Getter
public class ClubDto {
    // Getters and Setters
    private String clubName;
    private String location;
    private String phoneNumber;
    private String website;
    private String notice;
    private Long ownerId;

    public void setOwnerId(Long ownerId) {
        this.ownerId = ownerId;
    }

    public void setClubName(String clubName) {
        this.clubName = clubName;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }
}
