package woowoong.slacker.dto;

import woowoong.slacker.domain.User;

public class UserResponse {
    private String email;
    private String nickname;
    private String role;

    public UserResponse(User user) {
        this.email = user.getEmail();
        this.nickname = user.getUsername();
        this.role = user.getRole().toString();
    }

    // 게터와 세터
}
