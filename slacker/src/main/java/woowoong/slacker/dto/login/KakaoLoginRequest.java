package woowoong.slacker.dto.login;

import lombok.Getter;
import woowoong.slacker.domain.Role;

// 액세스 토큰 처리 요청 데이터 클래스
@Getter
public class KakaoLoginRequest {
    private String accessToken;
    private Role role;
    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    // 게터와 세터
}

