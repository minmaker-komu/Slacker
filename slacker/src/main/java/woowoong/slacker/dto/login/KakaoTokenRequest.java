package woowoong.slacker.dto.login;

import lombok.Getter;

// 액세스 토큰 처리 요청 데이터 클래스
@Getter
public class KakaoTokenRequest {
    private String accessToken;

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    // 게터와 세터
}

