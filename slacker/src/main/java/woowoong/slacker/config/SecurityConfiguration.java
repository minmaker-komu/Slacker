//package woowoong.slacker.config;
//
//
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//import woowoong.slacker.service.CustomOAuth2UserService;
//
//@Configuration
//public class SecurityConfiguration {
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf(csrf -> csrf.disable())  // CSRF 비활성화
//                .authorizeHttpRequests(auth -> auth
//                        .requestMatchers("/auth/**", "/public/**").permitAll()  // 특정 경로 인증 없이 허용
//                        .anyRequest().authenticated()  // 그 외의 요청은 인증 필요
//                )
//                .oauth2Login(oauth2 -> oauth2
//                        .userInfoEndpoint(userInfo -> userInfo
//                                .userService(customOAuth2UserService())  // 사용자 정보 처리 서비스
//                        )
//                        .defaultSuccessUrl("/auth/success")  // 로그인 성공 후 리다이렉트 경로
//                );
//
//        return http.build();  // HttpSecurity 설정 후 반환
//    }
//
//    // CustomOAuth2UserService를 빈으로 등록
//    @Bean
//    public CustomOAuth2UserService customOAuth2UserService() {
//        return new CustomOAuth2UserService();
//    }
//}
