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
    private String username;
    private String email;
    @Enumerated(EnumType.STRING)
    private Role role; // USER, OWNER

    @OneToMany(mappedBy = "user")
    private List<Booking> bookings;  // 유저가 예매한 예매 내역들

    public User() {
    }

    public User(Long id, String username, String email, Role role) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.role = role;
    }

    // 생성자
    @Builder
    public User(String username, String email, Role role) {
        this.username = username;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}