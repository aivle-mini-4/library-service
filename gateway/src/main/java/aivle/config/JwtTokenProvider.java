package aivle.config;

import java.nio.charset.StandardCharsets;
import java.security.Key;
import java.util.Date;

import javax.crypto.spec.SecretKeySpec;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@Component
public class JwtTokenProvider {
    
    @Value("${jwt.secret}")
    private String secret;
    
    public boolean validateToken(String token) {
        try {
            Key key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), 
                                       SignatureAlgorithm.HS256.getJcaName());
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (Exception e) {
            return false;
        }
    }
    
    public boolean isExpiration(String token) {
        try {
            Key key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), 
                                       SignatureAlgorithm.HS256.getJcaName());
            Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                           .parseClaimsJws(token).getBody();
            return claims.getExpiration().before(new Date());
        } catch (Exception e) {
            return true;
        }
    }
    
    public String getLoginId(String token) {
        Key key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), 
                                   SignatureAlgorithm.HS256.getJcaName());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                       .parseClaimsJws(token).getBody();
        return claims.getSubject();
    }
    
    public String getRoles(String token) {
        Key key = new SecretKeySpec(secret.getBytes(StandardCharsets.UTF_8), 
                                   SignatureAlgorithm.HS256.getJcaName());
        Claims claims = Jwts.parserBuilder().setSigningKey(key).build()
                       .parseClaimsJws(token).getBody();
        return claims.get("role", String.class);
    }
} 