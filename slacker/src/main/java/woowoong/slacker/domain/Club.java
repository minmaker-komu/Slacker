package woowoong.slacker.domain;

import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import org.springframework.data.annotation.Id;

public class Club {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String clubName;
    private String location;
    private String phoneNumber;
    private String website;
    private String notice;
}
