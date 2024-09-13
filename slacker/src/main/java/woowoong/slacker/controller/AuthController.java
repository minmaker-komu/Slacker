package woowoong.slacker.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import woowoong.slacker.domain.User;
import woowoong.slacker.dto.KakaoTokenRequest;
import woowoong.slacker.dto.KakaoUser;
import woowoong.slacker.dto.UserResponse;
import woowoong.slacker.service.KakaoService;
import woowoong.slacker.service.UserService;


//@RestController
//@RequestMapping("/oauth")
//public class AuthController {
//
//    @Autowired
//    private KakaoLoginService kakaoLoginService;
//
//    // 카카오 로그인 요청
//    @GetMapping("/kakao/login")
//    public ResponseEntity<KakaoUser> kakaoLogin(@RequestParam String code) {
//        System.out.println("요청 도달 디버깅: 인가 코드 수신");
//        System.out.println("카카오 로그인 요청이 도달했습니다.");
//        if(code == null){
//            System.out.println("인가코드를 받지 못했습니다.");
//        }
//
//        //인가 코드 확인
//        System.out.println("인가 코드: " +code);
//
//        // 1.인가 코드를 사용해 액세스 토큰 요청
//        String accessToken = kakaoLoginService.getAccessToken(code);
//        if (accessToken == null) {
//            System.out.println("액세스 토큰을 받지 못했습니다.");
//            return ResponseEntity.badRequest().body(null);
//        }
//
//        // 액세스 토큰 확인
//        System.out.println("받은 액세스 토큰: " + accessToken);
//
//        // 2. 액세스 토큰을 사용해 카카오 사용자 정보 요청 및 회원가입 처리
//        KakaoUser kakaoUser = kakaoLoginService.processKakaoUser(accessToken);
//
//        // 3. 사용자 정보 확인
//        System.out.println("카카오 사용자 정보: " + kakaoUser);
//
//        return ResponseEntity.ok(kakaoUser);
//    }
//
//}

@RestController
@RequestMapping("/auth")
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


