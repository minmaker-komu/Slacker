package woowoong.slacker.dto.login;

//카카오 API에서 받아오는 사용자 정보를 저장하기 위한 DTO 클래스

public class KakaoUser {
    private String email;
    private String nickname;

    // 게터와 세터

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
}

