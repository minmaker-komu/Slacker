package woowoong.slacker.dto.login;

import lombok.Getter;
import woowoong.slacker.domain.User;

@Getter
// 로그인 후 사용자 정보 반환
public class UserResponse {
    private Long id;
    private Long kakaoId;
    private String userName;
    private String role;

    public UserResponse(User user) {
        this.id = user.getId();
        this.kakaoId = user.getKakaoId();
        this.userName = user.getUsername();
        this.role = user.getRole().toString();
    }

    // 게터와 세터
}
