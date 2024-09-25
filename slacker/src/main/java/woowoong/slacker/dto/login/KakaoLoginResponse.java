package woowoong.slacker.dto.login;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

// 첫 로그인 여부 응답 데이터 클래스
@Getter
@Setter
@AllArgsConstructor
public class KakaoLoginResponse {
    private UserResponse userResponse;
    private String isFirstLogin; // 첫 로그인 여부
}
