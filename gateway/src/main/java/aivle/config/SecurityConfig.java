package aivle.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@Configuration
@EnableWebFluxSecurity
public class SecurityConfig {

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http
            .csrf().disable()  // CSRF 비활성화
            .authorizeExchange()
                .anyExchange().permitAll()  // 모든 요청 허용 (JWT 필터에서 권한 체크)
            .and()
            .httpBasic().disable()  // HTTP Basic 인증 비활성화
            .formLogin().disable();  // 폼 로그인 비활성화
        
        return http.build();
    }
} 