//package woowoong.slacker.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.builders.HttpSecurity;
//import org.springframework.security.web.SecurityFilterChain;
//
//@Configuration
//public class SecurityConfiguration {
//
//    @Autowired
//    private CustomOAuth2UserService customOAuth2UserService;
//
//    @Bean
//    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
//        http
//                .csrf().disable()
//                .authorizeRequests()
//                .antMatchers("/auth/**", "/public/**").permitAll()  // 로그인 엔드포인트와 공개 리소스는 모두 접근 가능
//                .anyRequest().authenticated()  // 그 외 요청은 인증이 필요함
//                .and()
//                .oauth2Login()
//                .userInfoEndpoint()
//                .userService(customOAuth2UserService)  // 커스터마이징한 OAuth2UserService 적용
//                .and()
//                .defaultSuccessUrl("/main", true);  // 로그인 성공 시 이동할 URL 설정
//
//        return http.build();
//    }
//}
