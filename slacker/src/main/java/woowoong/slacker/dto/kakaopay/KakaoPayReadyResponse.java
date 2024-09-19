package woowoong.slacker.dto.kakaopay;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

// 결제 요청 시 받는 정보
@Getter
@Setter
@ToString
public class KakaoPayReadyResponse {
    private String tid; // 결제 고유 번호
    private String next_redirect_mobile_url; // 모바일 웹일 경우 받는 결제페이지 url
    private String next_redirect_pc_url; // pc 웹일 경우 받는 결제 페이지 url
    private String android_app_scheme; // 결제 화면으로 이동하는 Android 앱 스킴(Scheme) - 내부 서비스용
    private String created_at;
}
