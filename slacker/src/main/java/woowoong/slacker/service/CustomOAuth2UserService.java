//package woowoong.slacker.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
//import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
//import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
//import org.springframework.security.oauth2.core.user.OAuth2User;
//import org.springframework.stereotype.Service;
//import woowoong.slacker.domain.Role;
//import woowoong.slacker.domain.User;
//import woowoong.slacker.repository.UserRepository;
//
//import java.util.Map;
//import java.util.Optional;
//
//@Service
//public class CustomOAuth2UserService implements OAuth2UserService<OAuth2UserRequest, OAuth2User> {
//
//    @Autowired
//    private UserRepository userRepository;
//
//    @Override
//    public OAuth2User loadUser(OAuth2UserRequest userRequest) throws OAuth2AuthenticationException {
//        OAuth2UserService<OAuth2UserRequest, OAuth2User> delegate = new DefaultOAuth2UserService();
//        OAuth2User oAuth2User = delegate.loadUser(userRequest);
//
//        // 카카오 사용자 정보 파싱
//        Map<String, Object> kakaoAccount = (Map<String, Object>) oAuth2User.getAttribute("kakao_account");
//        Map<String, Object> profile = (Map<String, Object>) kakaoAccount.get("profile");
//
//        String userName = (String) profile.get("nickname");
//        String email = (String) kakaoAccount.get("email");
//
//        // 사용자 정보가 데이터베이스에 존재하는지 확인
//        Optional<User> userOptional = userRepository.findByEmail(email);
//        User user;
//        if (userOptional.isPresent()) {
//            user = userOptional.get();
//        } else {
//            // 데이터베이스에 사용자 정보가 없으면 새로 저장
//            user = new User(userName, email, Role.USER);
//            userRepository.save(user);
//        }
//
//        return oAuth2User; // 인증된 OAuth2User 객체 반환
//    }
//}
