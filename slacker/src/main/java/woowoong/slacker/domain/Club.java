package woowoong.slacker.domain;

import jakarta.persistence.*;
import lombok.Getter;


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
}
