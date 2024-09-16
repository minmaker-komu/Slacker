package woowoong.slacker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowoong.slacker.domain.User;
import woowoong.slacker.dto.KakaoTokenRequest;
import woowoong.slacker.dto.KakaoUser;
import woowoong.slacker.dto.UserResponse;
import woowoong.slacker.service.KakaoService;
import woowoong.slacker.service.UserService;

@RestController
@RequestMapping("/oauth")
public class AuthController {

    @Autowired
    private KakaoService kakaoService;

    @Autowired
    private UserService userService;

    @PostMapping("/kakao")
    public ResponseEntity<UserResponse> kakaoLogin(@RequestBody KakaoTokenRequest kakaoTokenRequest) {
        String kakaoAccessToken = kakaoTokenRequest.getAccessToken();

        // 카카오 API로부터 사용자 정보 가져오기
        KakaoUser kakaoUser = kakaoService.getUserInfo(kakaoAccessToken);
        System.out.println("사용자 정보" + kakaoUser);

        // 사용자 정보 처리 및 저장
        User user = userService.processKakaoLogin(kakaoUser);

        // 적절한 사용자 정보 응답
        return ResponseEntity.ok(new UserResponse(user));
    }
}


