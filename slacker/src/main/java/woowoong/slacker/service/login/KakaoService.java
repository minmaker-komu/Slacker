package woowoong.slacker.service.login;

import io.netty.handler.codec.http.HttpHeaderValues;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import woowoong.slacker.dto.login.KakaoUserInfoResponse;

@Service
public class KakaoService {
    static String secretKey = "Bearer ";

    public KakaoUserInfoResponse getUserInfo(String accessToken) {
        String requestUrl = "https://kapi.kakao.com";
        secretKey = secretKey + accessToken;

        KakaoUserInfoResponse userInfo = WebClient.create(requestUrl)
                .get()
                .uri(uriBuilder -> uriBuilder
                        .scheme("https")
                        .path("/v2/user/me")
                        .build(true))
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken) // access token 인가
                .header(HttpHeaders.CONTENT_TYPE, HttpHeaderValues.APPLICATION_X_WWW_FORM_URLENCODED.toString())
                .retrieve()
                .bodyToMono(KakaoUserInfoResponse.class)
                .block();

        return userInfo;
    }
}

