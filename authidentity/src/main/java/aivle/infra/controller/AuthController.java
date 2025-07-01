package aivle.infra.controller;

import java.util.HashMap;
import java.util.Map;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aivle.infra.security.CustomUserDetails;
import aivle.infra.security.JwtTokenUtil;
import aivle.infra.security.JwtUserDetailsService;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Transactional
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private JwtUserDetailsService userDetailsService;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {
        authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String token = jwtTokenUtil.generateTokenWithUserIdOnly(userDetails);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("email", loginRequest.getEmail());
        response.put("userId", ((CustomUserDetails) userDetails).getUserId());
        response.put("role", userDetails.getAuthorities().iterator().next().getAuthority());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            System.out.println("=== Token Validation Debug ===");
            System.out.println("Original token: " + token);
            
            String jwtToken = token.substring(7);
            System.out.println("JWT token: " + jwtToken);
            
            String userId = jwtTokenUtil.extractUserId(jwtToken);
            System.out.println("Extracted userId: " + userId);
            
            // 사용자 ID로 사용자 정보 조회
            UserDetails userDetails = userDetailsService.loadUserByUserId(userId);
            System.out.println("UserDetails found: " + userDetails.getUsername());
            
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                Map<String, Object> response = new HashMap<>();
                response.put("valid", true);
                response.put("userId", userId);
                response.put("email", userDetails.getUsername());
                response.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
                return ResponseEntity.ok(response);
            } else {
                System.out.println("Token validation failed");
                return ResponseEntity.badRequest().body("Invalid token");
            }
        } catch (Exception e) {
            System.out.println("Exception during token validation: " + e.getMessage());
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Invalid token: " + e.getMessage());
        }
    }

    private void authenticate(String email, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new Exception("USER_DISABLED", e);
        } catch (BadCredentialsException e) {
            throw new Exception("INVALID_CREDENTIALS", e);
        }
    }

    // Request DTOs
    public static class LoginRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
} 