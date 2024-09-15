package woowoong.slacker.dto;

// 액세스 토큰 처리 요청 데이터 클래스
public class KakaoTokenRequest {
    private String accessToken;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
    // 게터와 세터
}

