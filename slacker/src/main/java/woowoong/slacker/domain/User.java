package woowoong.slacker.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;

import java.util.List;

@Entity
@Getter
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long kakaoId;
    private String username;
    @Enumerated(EnumType.STRING)
    private Role role; // USER, OWNER

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;  // 유저가 예매한 예매 내역들

    public User() {
    }

    public User(Long id, Long kakaoId, String username, Role role) {
        this.id = id;
        this.kakaoId = kakaoId;
        this.username = username;
        this.role = role;
    }

    // 생성자
    @Builder
    public User(Long kakaoId, String username, Role role) {
        this.kakaoId = kakaoId;
        this.username = username;
        this.role = role;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

}