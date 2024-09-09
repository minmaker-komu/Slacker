package woowoong.slacker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowoong.slacker.dto.KakaoTokenRequest;
import woowoong.slacker.dto.KakaoUser;
import woowoong.slacker.service.KakaoLoginService;



@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Autowired
    private KakaoLoginService kakaoLoginService;

    @GetMapping("/kakao/login")
    public ResponseEntity<KakaoUser> kakaoLogin(@RequestBody KakaoTokenRequest kakaoTokenRequest) {
        // 1. 인가 코드를 사용해 액세스 토큰 요청
        String accessToken = kakaoLoginService.getAccessToken(kakaoTokenRequest.getCode());
        if (accessToken == null) {
            return ResponseEntity.badRequest().body(null);
        }

        // 2. 액세스 토큰을 사용해 카카오 사용자 정보 요청 및 회원가입 처리
        KakaoUser kakaoUser = kakaoLoginService.processKakaoUser(accessToken);

        return ResponseEntity.ok(kakaoUser);
    }
}
