package aivle.config;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.server.authorization.AuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import reactor.core.publisher.Mono;

@Component
public class JwtAuthenticationFilter extends AbstractGatewayFilterFactory<JwtAuthenticationFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(JwtAuthenticationFilter.class);
    private final JwtTokenProvider jwtProvider;
    private final SecurityProperties securityProperties;
    private final ReactiveAuthorizationManager<AuthorizationContext> authorizationManager;

    public JwtAuthenticationFilter(JwtTokenProvider jwtProvider, SecurityProperties securityProperties) {
        super(Config.class);
        this.jwtProvider = jwtProvider;
        this.securityProperties = securityProperties;
        this.authorizationManager = new PathBasedAuthorizationManager(securityProperties);
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

                // Spring Security 기반 권한 체크
                Authentication authentication = createAuthentication(userId, role);
                AuthorizationContext context = new AuthorizationContext(exchange);
                
                return authorizationManager.check(Mono.just(authentication), context)
                    .flatMap(decision -> {
                        if (decision.isGranted()) {
                            // 헤더에 사용자 정보 추가
                            ServerHttpRequest modifiedRequest = exchange.getRequest().mutate()
                                    .header("X-User-Id", userId)
                                    .header("X-User-Role", role)
                                    .build();
                            
                            ServerWebExchange modifiedExchange = exchange.mutate().request(modifiedRequest).build();
                            return chain.filter(modifiedExchange);
                        } else {
                            log.warn("접근 권한 없음: role={}, path={}", role, path);
                            return onError(exchange, "해당 경로에 접근할 권한이 없습니다", HttpStatus.FORBIDDEN);
                        }
                    });
            } else {
                log.info("Authorization 헤더 없음 또는 Bearer 토큰 아님");
                return onError(exchange, "인증이 필요합니다", HttpStatus.UNAUTHORIZED);
            }
        };
    }

    private boolean isProtectedPath(String path) {
        List<String> protectedPaths = securityProperties.getProtectedPaths();
        return protectedPaths != null && protectedPaths.stream().anyMatch(path::startsWith);
    }

    private Authentication createAuthentication(String userId, String role) {
        return new org.springframework.security.authentication.UsernamePasswordAuthenticationToken(
            userId, null, Arrays.asList(new SimpleGrantedAuthority(role))
        );
    }

    private Mono<Void> onError(ServerWebExchange exchange, String err, HttpStatus httpStatus) {
        exchange.getResponse().setStatusCode(httpStatus);
        return exchange.getResponse().setComplete();
    }

    // Spring Security 기반 권한 관리자
    private static class PathBasedAuthorizationManager implements ReactiveAuthorizationManager<AuthorizationContext> {
        private final SecurityProperties securityProperties;

        public PathBasedAuthorizationManager(SecurityProperties securityProperties) {
            this.securityProperties = securityProperties;
        }

        @Override
        public Mono<AuthorizationDecision> check(Mono<Authentication> authentication, AuthorizationContext context) {
            return authentication.map(auth -> {
                String path = context.getExchange().getRequest().getPath().value();
                String role = auth.getAuthorities().stream()
                    .findFirst()
                    .map(Object::toString)
                    .orElse("");

                // ROLE_ 접두사 제거
                String roleWithoutPrefix = role.startsWith("ROLE_") ? role.substring(5) : role;
                
                Map<String, List<String>> rolePaths = securityProperties.getRolePaths();
                if (rolePaths == null || roleWithoutPrefix == null) {
                    return new AuthorizationDecision(true); // 설정이 없으면 모든 접근 허용
                }
                
                // 내 role에 해당 path가 설정되어 있는지 확인
                List<String> myAllowedPaths = rolePaths.get(roleWithoutPrefix);
                boolean hasMyAccess = myAllowedPaths != null && myAllowedPaths.stream().anyMatch(path::startsWith);
                
                // 다른 role에 해당 path가 설정되어 있는지 확인
                boolean hasOtherAccess = rolePaths.entrySet().stream()
                    .filter(entry -> !entry.getKey().equals(roleWithoutPrefix)) // 내 role 제외
                    .anyMatch(entry -> entry.getValue() != null && entry.getValue().stream().anyMatch(path::startsWith));
                
                // 내 role에 설정되어 있으면 무조건 접근 허용
                if (hasMyAccess) {
                    return new AuthorizationDecision(true);
                }
                
                // 내 role에 없고 다른 role에도 없으면 접근 허용
                if (!hasOtherAccess) {
                    return new AuthorizationDecision(true);
                }
                
                // 내 role에 없고 다른 role에만 있으면 접근 거부
                return new AuthorizationDecision(false);
            });
        }
    }

    public static class Config {
        // 설정이 필요한 경우 여기에 추가
    }
} 