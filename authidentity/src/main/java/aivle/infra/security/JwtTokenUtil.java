package aivle.infra.security;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenUtil {

    @Value("${jwt.secret:defaultSecretKey}")
    private String secret;

    @Value("${jwt.expiration:86400}")
    private Long expiration;

    // 사용자 ID 추출 (subject)
    public String extractUserId(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        Key key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token).getBody();
    }

    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private String createToken(Map<String, Object> claims, String subject) {
        Key key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), SignatureAlgorithm.HS256.getJcaName());
        return Jwts.builder()
                .setClaims(claims)
                .setSubject(subject)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expiration * 1000))
                .signWith(key)
                .compact();
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        final String userId = extractUserId(token);
        String userDetailsId = extractUserIdFromUserDetails(userDetails);
        return (userId.equals(userDetailsId) && !isTokenExpired(token));
    }

    // UserDetails에서 사용자 ID 추출 (CustomUserDetails 사용)
    private String extractUserIdFromUserDetails(UserDetails userDetails) {
        if (userDetails instanceof CustomUserDetails) {
            return ((CustomUserDetails) userDetails).getUserId();
        }
        // 기존 User 클래스 사용 시 이메일을 ID로 사용 (임시)
        return userDetails.getUsername();
    }

    // 사용자 ID와 역할을 담는 토큰 생성
    public String generateTokenWithUserIdOnly(String userId, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, userId);
    }

    // UserDetails로부터 사용자 ID와 역할을 담는 토큰 생성
    public String generateTokenWithUserIdOnly(UserDetails userDetails) {
        String userId = extractUserIdFromUserDetails(userDetails);
        String role = extractRoleFromUserDetails(userDetails);
        return generateTokenWithUserIdOnly(userId, role);
    }

    // UserDetails에서 역할 추출
    private String extractRoleFromUserDetails(UserDetails userDetails) {
        if (userDetails.getAuthorities() != null && !userDetails.getAuthorities().isEmpty()) {
            return userDetails.getAuthorities().iterator().next().getAuthority();
        }
        return "ROLE_USER"; // 기본값
    }

    // 토큰에서 역할 추출
    public String extractRole(String token) {
        return extractClaim(token, claims -> claims.get("role", String.class));
    }
} 