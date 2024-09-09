package woowoong.slacker.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import woowoong.slacker.domain.Role;
import woowoong.slacker.domain.User;
import woowoong.slacker.dto.KakaoTokenRequest;
import woowoong.slacker.dto.KakaoUser;
import woowoong.slacker.repository.UserRepository;

import java.util.Map;
import java.util.Optional;

@Service
public class KakaoLoginService {

    private final RestTemplate restTemplate;
    private final UserRepository userRepository;
    private final String clientId = "30f7417d4dfd32ed9ffa1c968a29faf3";  // 카카오에서 발급받은 클라이언트 ID
    private final String redirectUri = "http://localhost:8080/oauth/kakao/login";
    private final String clientSecret = "ptNI8ZjOnGbCNsmvRyE5e4rkgkWeCchr"; // 선택 사항
    private final String kakaoTokenUrl = "https://kauth.kakao.com/oauth/token";
    private final String kakaoUserInfoUrl = "https://kapi.kakao.com/v2/user/me";

    @Autowired
    public KakaoLoginService(RestTemplate restTemplate, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }

    // 액세스 토큰 요청
    public String getAccessToken(String code) {
        KakaoTokenRequest tokenRequest = new KakaoTokenRequest(
                "authorization_code",
                clientId,
                redirectUri,
                code,
                clientSecret
        );

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("grant_type", tokenRequest.getGrant_type());
        params.add("client_id", tokenRequest.getClient_id());
        params.add("redirect_uri", tokenRequest.getRedirect_uri());
        params.add("code", tokenRequest.getCode());
        if (tokenRequest.getClient_secret() != null) {
            params.add("client_secret", tokenRequest.getClient_secret());
        }

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);
        ResponseEntity<Map> response = restTemplate.postForEntity(kakaoTokenUrl, request, Map.class);

        Map<String, Object> responseBody = response.getBody();
        System.out.println("카카오 로그인 성공");
        return responseBody != null ? (String) responseBody.get("access_token") : null;
    }

    // 카카오 사용자 정보 요청 및 회원가입 처리
    public KakaoUser processKakaoUser(String accessToken) {
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(kakaoUserInfoUrl, HttpMethod.GET, request, Map.class);

        Map<String, Object> userInfo = response.getBody();
        Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");

        String nickname = (String) ((Map<String, Object>) kakaoAccount.get("profile")).get("nickname");
        String email = (String) kakaoAccount.get("email");

        // 카카오 사용자 정보를 기반으로 새로운 사용자 생성
        KakaoUser kakaoUser = new KakaoUser((Long) userInfo.get("id"), nickname, email, Role.USER);

        // 데이터베이스에 사용자 존재 여부 확인
        Optional<User> existingUser = userRepository.findByEmail(kakaoUser.getEmail());
        if (!existingUser.isPresent()) {
            // 데이터베이스에 저장
            User newUser = new User(kakaoUser.getId(),kakaoUser.getUserName(), kakaoUser.getEmail(), kakaoUser.getRole());
            userRepository.save(newUser);
        }

        return kakaoUser;
    }
}
