package aivle.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.support.ServerWebExchangeUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;

import reactor.core.publisher.Mono;

@Component
public class LoggingFilter extends AbstractGatewayFilterFactory<LoggingFilter.Config> {

    private static final Logger log = LoggerFactory.getLogger(LoggingFilter.class);

    public LoggingFilter() {
        super(Config.class);
    }

    @Override
    public GatewayFilter apply(Config config) {
        return (exchange, chain) -> {
            ServerHttpRequest request = exchange.getRequest();
            String path = request.getPath().value();
            String method = request.getMethod().name();
            String remoteAddr = String.valueOf(request.getRemoteAddress());

            // 쿼리 요약
            StringBuilder queryStr = new StringBuilder();
            request.getQueryParams().forEach((key, values) -> {
                if (queryStr.length() > 0) queryStr.append(", ");
                queryStr.append(key).append("=").append(values);
            });

            // 라우트 목적지 URI
            Route route = exchange.getAttribute(ServerWebExchangeUtils.GATEWAY_ROUTE_ATTR);
            String targetUri = (route != null) ? route.getUri().toString() : "UNKNOWN";

            log.info("[GATEWAY] {} {} FROM={} QUERY={{}} ROUTE_TO={}", 
                    method, path, remoteAddr, queryStr, targetUri);

            // 필터 체인 실행 후 최종 헤더 확인
            return chain.filter(exchange).then(Mono.fromRunnable(() -> {
                ServerHttpRequest finalRequest = exchange.getRequest();
                StringBuilder finalHeaderStr = new StringBuilder();
                HttpHeaders finalHeaders = finalRequest.getHeaders();
                
                finalHeaders.forEach((key, values) -> {
                    if (finalHeaderStr.length() > 0) finalHeaderStr.append(", ");
                    if ("authorization".equalsIgnoreCase(key)) {
                        String token = values.isEmpty() ? "" : values.get(0);
                        String masked = token.length() > 20 ? token.substring(0, 20) + "..." : token;
                        finalHeaderStr.append(key).append("=").append(masked);
                    } else {
                        // 헤더 값을 일관성 있게 포맷팅 (첫 번째 값만 사용)
                        String value = values.isEmpty() ? "" : values.get(0);
                        finalHeaderStr.append(key).append("=").append(value);
                    }
                });
                
                log.info("[GATEWAY] {} {} HEADERS={{}}", method, path, finalHeaderStr);
            }));
        };
    }

    public static class Config {
        // 설정이 필요한 경우 여기에 추가
    }
} 