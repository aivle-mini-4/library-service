package aivle.config;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider jwtProvider;
    private final SecurityProperties securityProperties;

    public JwtAuthenticationFilter(JwtTokenProvider jwtProvider, SecurityProperties securityProperties) {
        super(Config.class);
        this.jwtProvider = jwtProvider;
        this.securityProperties = securityProperties;
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            String path = exchange.getRequest().getPath().value();
            
            // 인증이 필요없는 경로들은 바로 통과
            if (!isProtectedPath(path)) {
                return chain.filter(exchange);
            }
            
            String authorizationHeader = exchange.getRequest().getHeaders()
                                               .getFirst(HttpHeaders.AUTHORIZATION);
            
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                String token = authorizationHeader.substring(7);

                // 토큰 만료 체크
                if (jwtProvider.isExpiration(token)) {
                    log.info("access token 만료");
                    return onError(exchange, "토큰이 만료되었습니다", HttpStatus.UNAUTHORIZED);
                }

                // 토큰 유효성 검증
                if (!jwtProvider.validateToken(token)) {
                    log.info("유효하지 않은 토큰");
                    return onError(exchange, "유효하지 않은 토큰입니다", HttpStatus.UNAUTHORIZED);
                }

                // 사용자 정보 추출
                String userId = jwtProvider.getLoginId(token);
                String role = jwtProvider.getRoles(token);

                log.info("인증된 사용자: userId={}, role={}", userId, role);

                // 헤더에 사용자 정보 추가
                ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                        .header("X-User-Id", userId)
                        .header("X-User-Role", role)
                        .build();

                exchange = exchange.mutate().request(modifiedRequest).build();
            } else {
                log.info("Authorization 헤더 없음 또는 Bearer 토큰 아님");
                return onError(exchange, "인증이 필요합니다", HttpStatus.UNAUTHORIZED);
            }
            
            return chain.filter(exchange);
        };
    }

    private boolean isProtectedPath(String path) {
        List<String> protectedPaths = securityProperties.getProtectedPaths();
        return protectedPaths != null && protectedPaths.stream().anyMatch(path::startsWith);
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        return exchange.getResponse().setComplete();
    }

    public static class Config {
        // 설정이 필요한 경우 여기에 추가
    }
} 