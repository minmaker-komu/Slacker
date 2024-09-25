package woowoong.slacker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowoong.slacker.domain.Role;
import woowoong.slacker.domain.User;
import woowoong.slacker.dto.login.KakaoLoginRequest;
import woowoong.slacker.dto.login.KakaoLoginResponse;
import woowoong.slacker.dto.login.KakaoUserInfoResponse;
import woowoong.slacker.dto.login.UserResponse;
import woowoong.slacker.service.login.KakaoService;
import woowoong.slacker.service.login.UserService;

@RestController
@RequestMapping("/oauth")
public class AuthController {
    private final KakaoService kakaoService;
    private final UserService userService;

    @Autowired
    public AuthController(KakaoService kakaoService, UserService userService) {
        this.kakaoService = kakaoService;
        this.userService = userService;
    }

    // 회원가입
    @PostMapping("/kakao")
    public ResponseEntity<KakaoLoginResponse> kakaoLogin(@RequestBody KakaoLoginRequest kakaoLoginRequest) {
        String kakaoAccessToken = kakaoLoginRequest.getAccessToken();
        Role role = kakaoLoginRequest.getRole();

        // 카카오 API로부터 사용자 정보 가져오기
        KakaoUserInfoResponse kakaoUser = kakaoService.getUserInfo(kakaoAccessToken);

        // 사용자 정보 처리 및 저장
        KakaoLoginResponse user = userService.processKakaoLogin(kakaoUser, role);

        System.out.println(user.getIsFirstLogin());

        // 적절한 사용자 정보 응답
        return ResponseEntity.ok(user);
    }

    // 로그인

}


