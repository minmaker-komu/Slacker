package woowoong.slacker.service;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import woowoong.slacker.dto.login.KakaoUser;

@Service
public class KakaoService {

    private final RestTemplate restTemplate = new RestTemplate();

    public KakaoUser getUserInfo(String accessToken) {
        String requestUrl = "https://kapi.kakao.com/v2/user/me";

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<KakaoUser> response = restTemplate.exchange(requestUrl, HttpMethod.GET, entity, KakaoUser.class);

        return response.getBody();
    }
}

