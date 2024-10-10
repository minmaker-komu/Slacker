package woowoong.slacker.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Getter
public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String clubName;
    private String location;
    private String phoneNumber;
    private String website;
    private String notice;

    @OneToOne
    @JoinColumn(name = "owner_id", referencedColumnName = "id")
    private User owner;



    public Club(String clubName, String location, String phoneNumber, String website, String notice, User owner) {
        this.clubName = clubName;
        this.location = location;
        this.phoneNumber = phoneNumber;
        this.website = website;
        this.notice = notice;
        this.owner = owner;
    }

    public Club() {

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
