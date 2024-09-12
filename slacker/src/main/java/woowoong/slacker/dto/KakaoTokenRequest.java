package woowoong.slacker.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

//카카오로부터 인가 코드를 받은 후, 이 코드를 사용하여 액세스 토큰을 요청하기 위해 필요한 데이터를 담는 객체
@Getter
@NoArgsConstructor
public class KakaoTokenRequest {
    private String grant_type;
    private String client_id;
    private String redirect_uri;
    private String code;
    //private String client_secret;

    @Builder
    public KakaoTokenRequest(String grant_type, String client_id, String redirect_uri, String code) {
        this.grant_type = grant_type;
        this.client_id = client_id;
        this.redirect_uri = redirect_uri;
        this.code = code;
        //this.client_secret = client_secret;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public String getClient_id() {
        return client_id;
    }

    public String getRedirect_uri() {
        return redirect_uri;
    }

    public String getCode() {
        return code;
    }

//    public String getClient_secret() {
//        return client_secret;
//    }
}

