package woowoong.slacker.service;

//카카오에서 받은 사용자 정보를 통해 사용자 데이터를 처리합니다.
// 여기서 일반 사용자와 사장님을 구분하고, DB에 사용자 정보를 저장

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import woowoong.slacker.domain.Role;
import woowoong.slacker.domain.User;
import woowoong.slacker.dto.login.KakaoUser;
import woowoong.slacker.repository.UserRepository;

import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public User processKakaoLogin(KakaoUser kakaoUser) {
        String email = kakaoUser.getEmail();
        String nickname = kakaoUser.getNickname();

        // 사용자 정보가 이미 있는지 확인
        Optional<User> existingUser = userRepository.findByEmail(email);

        if (existingUser.isPresent()) {
            // 기존 사용자 업데이트
            User user = existingUser.get();
            user.setUsername(nickname);
            return userRepository.save(user);
        } else {
            // 새로운 사용자 등록
            User newUser = new User();
            newUser.setEmail(email);
            newUser.setUsername(nickname);
            newUser.setRole(Role.USER);  // 기본적으로 일반 사용자로 설정

            return userRepository.save(newUser);
        }
    }
}
