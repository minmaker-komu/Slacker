package woowoong.slacker.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import woowoong.slacker.domain.Role;

//카카오 API에서 받아오는 사용자 정보를 저장하기 위한 DTO 클래스

@Getter
@NoArgsConstructor
public class KakaoUser {

    private String userName;   // 카카오 닉네임
    private String email;      // 카카오 이메일
    private Role role;       // 기본값으로 ROLE_USER 설정

    @Builder
    public KakaoUser(String userName, String email, Role role) {
        this.userName = userName;
        this.email = email;
        this.role = Role.USER; // 기본적으로 일반 사용자로 설정
    }

    public String getUserName() {
        return userName;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}

