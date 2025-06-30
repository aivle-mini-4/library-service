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
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import aivle.domain.entity.UserAccount;
import aivle.domain.repository.AdminAccountRepository;
import aivle.domain.repository.AuthorAccountRepository;
import aivle.domain.repository.UserAccountRepository;
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

    @Autowired
    private UserAccountRepository userAccountRepository;

    @Autowired
    private AuthorAccountRepository authorAccountRepository;

    @Autowired
    private AdminAccountRepository adminAccountRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) throws Exception {
        authenticate(loginRequest.getEmail(), loginRequest.getPassword());

        final UserDetails userDetails = userDetailsService.loadUserByUsername(loginRequest.getEmail());
        final String token = jwtTokenUtil.generateToken(userDetails);

        Map<String, Object> response = new HashMap<>();
        response.put("token", token);
        response.put("email", loginRequest.getEmail());
        response.put("role", userDetails.getAuthorities().iterator().next().getAuthority());

        return ResponseEntity.ok(response);
    }

    @PostMapping("/signup")
    public ResponseEntity<?> signup(@RequestBody SignupRequest signupRequest) {
        try {
            // 이메일 중복 체크
            if (userAccountRepository.findByEmail(signupRequest.getEmail()).isPresent() ||
                authorAccountRepository.findByEmail(signupRequest.getEmail()).isPresent() ||
                adminAccountRepository.findByEmail(signupRequest.getEmail()).isPresent()) {
                return ResponseEntity.badRequest().body("Email already exists");
            }

            // 비밀번호 암호화
            String encodedPassword = passwordEncoder.encode(signupRequest.getPassword());

            // 사용자 계정 생성
            UserAccount userAccount = new UserAccount();
            userAccount.setEmail(signupRequest.getEmail());
            userAccount.setPassword(encodedPassword);
            userAccount.setCreatedAt(new java.util.Date());
            userAccount.setUpdatedAt(new java.util.Date());

            // 저장 후 flush하여 즉시 데이터베이스에 반영
            userAccountRepository.save(userAccount);
            userAccountRepository.flush();

            // 토큰 생성 (username과 role을 직접 전달)
            final String token = jwtTokenUtil.generateToken(signupRequest.getEmail(), "USER");

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("email", signupRequest.getEmail());
            response.put("role", "ROLE_USER");
            response.put("message", "User registered successfully");

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body("Registration failed: " + e.getMessage());
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        try {
            String jwtToken = token.substring(7);
            String username = jwtTokenUtil.extractUsername(jwtToken);
            UserDetails userDetails = userDetailsService.loadUserByUsername(username);
            
            if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                Map<String, Object> response = new HashMap<>();
                response.put("valid", true);
                response.put("email", username);
                response.put("role", userDetails.getAuthorities().iterator().next().getAuthority());
                return ResponseEntity.ok(response);
            } else {
                return ResponseEntity.badRequest().body("Invalid token");
            }
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Invalid token");
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

    public static class SignupRequest {
        private String email;
        private String password;

        public String getEmail() { return email; }
        public void setEmail(String email) { this.email = email; }
        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }
    }
} 