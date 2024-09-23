package woowoong.slacker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowoong.slacker.domain.User;
import woowoong.slacker.dto.login.KakaoTokenRequest;
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

    @PostMapping("/kakao")
    public ResponseEntity<UserResponse> kakaoLogin(@RequestBody KakaoTokenRequest kakaoTokenRequest) {
        String kakaoAccessToken = kakaoTokenRequest.getAccessToken();

        // 카카오 API로부터 사용자 정보 가져오기
        KakaoUserInfoResponse kakaoUser = kakaoService.getUserInfo(kakaoAccessToken);

        // 사용자 정보 처리 및 저장
        User user = userService.processKakaoLogin(kakaoUser);

        // 적절한 사용자 정보 응답
        return ResponseEntity.ok(new UserResponse(user));
    }
}


