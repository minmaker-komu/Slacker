package woowoong.slacker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
    private final String kakaoTokenUrl = "https://kauth.kakao.com/oauth/token";
    private final String kakaoUserInfoUrl = "https://kapi.kakao.com/v2/user/me";

    @Autowired
    public KakaoLoginService(RestTemplate restTemplate, UserRepository userRepository) {
        this.restTemplate = restTemplate;
        this.userRepository = userRepository;
    }

    // 액세스 토큰 요청
    public String getAccessToken(String code) {
        System.out.println("요청시작");
        System.out.println("액세스 토큰 요청을 위한 인가 코드: " + code);

        // 카카오 토큰 요청 파라미터 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        System.out.println("클라이언트 아이디입니다"+clientId);
        params.add("grant_type", "authorization_code");
        params.add("client_id", clientId);
        params.add("redirect_uri", redirectUri);
        params.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

        // 액세스 토큰 요청
        ResponseEntity<Map> response = restTemplate.postForEntity(kakaoTokenUrl, request, Map.class);
        Map<String, Object> responseBody = response.getBody();

        System.out.println("카카오 토큰 요청 응답: " + responseBody);

        return responseBody != null ? (String) responseBody.get("access_token") : null;
    }

    // 카카오 사용자 정보 요청 및 회원가입 처리
    public KakaoUser processKakaoUser(String accessToken) {

        System.out.println("카카오 사용자 정보 요청 - 액세스 토큰: " + accessToken);
        HttpHeaders headers = new HttpHeaders();
        headers.setBearerAuth(accessToken);

        HttpEntity<?> request = new HttpEntity<>(headers);
        ResponseEntity<Map> response = restTemplate.exchange(kakaoUserInfoUrl, HttpMethod.GET, request, Map.class);

        Map<String, Object> userInfo = response.getBody();
        System.out.println("카카오 사용자 정보 응답: " + userInfo);

        Map<String, Object> kakaoAccount = (Map<String, Object>) userInfo.get("kakao_account");
        String nickname = (String) ((Map<String, Object>) kakaoAccount.get("profile")).get("nickname");
        String email = (String) kakaoAccount.get("email");

        System.out.println("카카오 사용자 닉네임: " + nickname);
        System.out.println("카카오 사용자 이메일: " + email);

        KakaoUser kakaoUser = new KakaoUser(nickname, email, Role.USER);

        // 데이터베이스에 사용자 존재 여부 확인
        Optional<User> existingUser = userRepository.findByEmail(kakaoUser.getEmail());
        if (!existingUser.isPresent()) {
            User newUser = new User(kakaoUser.getUserName(), kakaoUser.getEmail(), kakaoUser.getRole());
            newUser.setEmail(email);
            newUser.setUsername(nickname);
            newUser.setRole(Role.USER);  // 기본적으로 일반 사용자로 설정
            userRepository.save(newUser);

            System.out.println("새로운 사용자가 데이터베이스에 저장되었습니다.");
        }

        return kakaoUser;
    }
}

