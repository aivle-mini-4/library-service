# JWT 인증 시스템 구현 상세 설명

## 개요
authidentity 서비스에 JWT(JSON Web Token) 기반 인증/인가 시스템을 구현했습니다. 이 시스템은 마이크로서비스 아키텍처에서 안전한 사용자 인증과 역할 기반 접근 제어를 제공합니다.

## 구현된 컴포넌트들

### 1. 의존성 추가 (pom.xml)

```xml
<!-- Spring Security -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-security</artifactId>
</dependency>

<!-- JWT 라이브러리 -->
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-api</artifactId>
    <version>0.11.5</version>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-impl</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>
<dependency>
    <groupId>io.jsonwebtoken</groupId>
    <artifactId>jjwt-jackson</artifactId>
    <version>0.11.5</version>
    <scope>runtime</scope>
</dependency>

<!-- Spring Web -->
<dependency>
    <groupId>org.springframework.boot</groupId>
    <artifactId>spring-boot-starter-web</artifactId>
</dependency>
```

**설명**: JWT 토큰 생성/검증과 Spring Security를 위한 필수 라이브러리들을 추가했습니다.

### 2. Spring Security 설정 (SecurityConfig.java)

```java
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    
    @Override
    protected void configure(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf().disable()
                .authorizeRequests()
                .antMatchers("/auth/**", "/h2-console/**").permitAll()
                .anyRequest().authenticated()
                .and()
                .exceptionHandling().authenticationEntryPoint(jwtAuthenticationEntryPoint)
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);

        httpSecurity.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }
}
```

**설명**: 
- `/auth/**` 경로는 인증 없이 접근 가능 (로그인, 회원가입용)
- 나머지 모든 요청은 인증 필요
- 세션 기반이 아닌 JWT 토큰 기반 인증 사용
- JWT 필터를 Spring Security 필터 체인에 추가

### 3. JWT 토큰 유틸리티 (JwtTokenUtil.java)

```java
@Component
public class JwtTokenUtil {
    
    @Value("${jwt.secret:defaultSecretKey}")
    private String secret;
    
    @Value("${jwt.expiration:86400}")
    private Long expiration;
    
    public String generateToken(String username, String role) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("role", role);
        return createToken(claims, username);
    }
    
    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
```

**설명**:
- JWT 토큰 생성, 검증, 파싱 기능 제공
- 토큰에 사용자 역할 정보 포함
- 설정 파일에서 secret key와 만료 시간 관리

### 4. JWT 인증 필터 (JwtAuthenticationFilter.java)

```java
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) {
        final String requestTokenHeader = request.getHeader("Authorization");
        
        if (requestTokenHeader != null && requestTokenHeader.startsWith("Bearer ")) {
            jwtToken = requestTokenHeader.substring(7);
            username = jwtTokenUtil.extractUsername(jwtToken);
            
            if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                
                if (jwtTokenUtil.validateToken(jwtToken, userDetails)) {
                    UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                    SecurityContextHolder.getContext().setAuthentication(authentication);
                }
            }
        }
        chain.doFilter(request, response);
    }
}
```

**설명**:
- 모든 HTTP 요청을 가로채서 JWT 토큰 검증
- `Authorization: Bearer <token>` 헤더에서 토큰 추출
- 토큰이 유효하면 Spring Security 컨텍스트에 인증 정보 설정

### 5. 사용자 상세 서비스 (JwtUserDetailsService.java)

```java
@Service
public class JwtUserDetailsService implements UserDetailsService {
    
    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        // UserAccount에서 먼저 찾기
        Optional<UserAccount> userAccount = userAccountRepository.findByEmail(email);
        if (userAccount.isPresent()) {
            return new User(userAccount.get().getEmail(), userAccount.get().getPassword(),
                    Arrays.asList(new SimpleGrantedAuthority("ROLE_" + userAccount.get().getRoles().name())));
        }
        
        // AuthorAccount, AdminAccount도 동일하게 처리
    }
}
```

**설명**:
- Spring Security가 사용자 정보를 로드할 때 사용
- UserAccount, AuthorAccount, AdminAccount 테이블에서 사용자 검색
- 각 사용자의 역할을 Spring Security 권한으로 변환

### 6. Repository 확장

모든 Account Repository에 `findByEmail` 메서드 추가:

```java
public interface UserAccountRepository extends PagingAndSortingRepository<UserAccount, Long> {
    Optional<UserAccount> findByEmail(String email);
}
```

**설명**: JWT 인증에서 이메일로 사용자를 찾기 위해 필요한 메서드입니다.

### 7. 인증 컨트롤러 (AuthController.java)

```java
@RestController
@RequestMapping("/auth")
public class AuthController {
    
    @PostMapping("/login")
    public ResponseEntity<?> createAuthenticationToken(@RequestBody LoginRequest loginRequest) {
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
        // 이메일 중복 체크
        // 비밀번호 암호화
        // 사용자 계정 생성
        // JWT 토큰 생성 및 반환
    }
    
    @PostMapping("/validate")
    public ResponseEntity<?> validateToken(@RequestHeader("Authorization") String token) {
        // 토큰 유효성 검증
    }
}
```

**설명**:
- `/auth/login`: 사용자 로그인 및 JWT 토큰 발급
- `/auth/signup`: 새 사용자 등록 및 자동 로그인
- `/auth/validate`: 토큰 유효성 검증

### 8. 비밀번호 암호화 (UserAccount.java)

```java
public void signup(SignupCommand signupCommand) {
    this.setEmail(signupCommand.getEmail());
    // 비밀번호 암호화
    org.springframework.security.crypto.password.PasswordEncoder passwordEncoder = 
        AuthidentityApplication.applicationContext.getBean(org.springframework.security.crypto.password.PasswordEncoder.class);
    this.setPassword(passwordEncoder.encode(signupCommand.getPassword()));
    this.setCreatedAt(new Date());
    this.setUpdatedAt(new Date());
    
    repository().save(this);
    SignedUp signedUp = new SignedUp(this);
    signedUp.publishAfterCommit();
}
```

**설명**: 회원가입 시 BCrypt로 비밀번호를 암호화하여 저장합니다.

### 9. 역할 기반 접근 제어

기존 컨트롤러들에 `@PreAuthorize` 어노테이션 추가:

```java
@RequestMapping(value = "/userAccounts/{id}/signup", method = RequestMethod.PUT)
@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
public UserAccount signup(...) { ... }

@RequestMapping(value = "/adminAccounts/{id}/signup", method = RequestMethod.PUT)
@PreAuthorize("hasRole('ADMIN')")
public AdminAccount signup(...) { ... }

@RequestMapping(value = "/authorAccounts/requestauthorregistration", method = RequestMethod.POST)
@PreAuthorize("hasRole('USER') or hasRole('AUTHOR')")
public AuthorAccount requestAuthorRegistration(...) { 
    // 작가회원가입 + 등록요청 이벤트 publish
    // - 이메일 중복 체크
    // - 비밀번호 암호화
    // - 작가 계정 생성 (AUTHOR 역할)
    // - AuthorRegistrationRequested 이벤트 publish
}
```

**설명**: 각 API 엔드포인트에 적절한 역할 권한을 설정했습니다.

### 10. JWT 설정 (application.yml)

```yaml
jwt:
  secret: aivleSecretKeyForJWTTokenGeneration2024
  expiration: 86400
```

**설명**: JWT 토큰의 시크릿 키와 만료 시간을 설정합니다.

## 인증 플로우

### 1. 로그인 플로우
1. 클라이언트가 `/auth/login`에 이메일/비밀번호 전송
2. Spring Security가 사용자 인증
3. 인증 성공 시 JWT 토큰 생성
4. 토큰과 사용자 정보를 클라이언트에 반환

### 2. API 요청 플로우
1. 클라이언트가 `Authorization: Bearer <token>` 헤더와 함께 API 요청
2. JwtAuthenticationFilter가 토큰 추출 및 검증
3. 토큰이 유효하면 Spring Security 컨텍스트에 인증 정보 설정
4. @PreAuthorize 어노테이션으로 역할 기반 권한 검증
5. 권한이 있으면 API 실행, 없으면 403 Forbidden 반환

### 3. 회원가입 플로우
1. 클라이언트가 `/auth/signup`에 이메일/비밀번호 전송
2. 이메일 중복 체크
3. 비밀번호 BCrypt 암호화
4. 사용자 계정 생성 및 저장
5. 자동 로그인하여 JWT 토큰 발급

## 보안 특징

1. **Stateless**: 서버에 세션 저장 없이 JWT 토큰만으로 인증
2. **암호화**: BCrypt로 비밀번호 안전하게 저장
3. **역할 기반**: USER, AUTHOR, ADMIN 역할별 접근 제어
4. **토큰 만료**: 설정 가능한 토큰 만료 시간
5. **시크릿 키**: 환경별로 다른 JWT 시크릿 키 사용 가능

## 사용 예시

### 로그인
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

응답:
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "email": "user@example.com",
  "role": "ROLE_USER"
}
```

### 보호된 API 접근
```bash
curl -X GET http://localhost:8080/userAccounts/1 \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

### 토큰 검증
```bash
curl -X POST http://localhost:8080/auth/validate \
  -H "Authorization: Bearer eyJhbGciOiJIUzUxMiJ9..."
```

## 확장 가능성

1. **토큰 갱신**: Refresh Token 구현 가능
2. **다중 인증**: OAuth2, 소셜 로그인 추가 가능
3. **토큰 블랙리스트**: 로그아웃 시 토큰 무효화
4. **감사 로그**: 인증 시도 로깅
5. **Rate Limiting**: 로그인 시도 제한

이 구현으로 마이크로서비스 환경에서 안전하고 확장 가능한 인증 시스템을 구축했습니다. 