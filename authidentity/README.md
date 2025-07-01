# authidentity

## JWT 인증 시스템

이 서비스는 JWT 토큰 기반 인증/인가 시스템을 제공합니다.

### 주요 기능
- JWT 토큰 기반 인증
- 역할 기반 접근 제어 (RBAC)
- 비밀번호 암호화 (BCrypt)
- 토큰 검증 및 갱신
- 마이크로서비스 간 이벤트 기반 통신 (Kafka)

## 아키텍처 개요

### 1. 전체 시스템 구조
```
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   Frontend      │    │   Gateway       │    │   Other MS      │
│   (Vue.js)      │◄──►│   (Spring Cloud)│◄──►│   (Books, etc.) │
└─────────────────┘    └─────────────────┘    └─────────────────┘
                                │
                                ▼
┌─────────────────────────────────────────────────────────────────┐
│                    authidentity                                 │
│  ┌─────────────┐  ┌─────────────┐  ┌─────────────┐            │
│  │   Controller│  │   Domain    │  │   Infra     │            │
│  │   Layer     │  │   Layer     │  │   Layer     │            │
│  └─────────────┘  └─────────────┘  └─────────────┘            │
└─────────────────────────────────────────────────────────────────┘
                                │
                                ▼
┌─────────────────┐    ┌─────────────────┐    ┌─────────────────┐
│   H2 Database   │    │   Kafka         │    │   JWT Token     │
│   (In-Memory)   │    │   (Events)      │    │   (Stateless)   │
└─────────────────┘    └─────────────────┘    └─────────────────┘
```

### 2. 계층별 아키텍처 (Clean Architecture)

#### Controller Layer (Inbound Adapter)
```
┌─────────────────────────────────────────────────────────────┐
│                    Controller Layer                         │
├─────────────────────────────────────────────────────────────┤
│ • AuthController        - 통합 인증 API (/auth/*)           │
│ • UserAccountController - 사용자 계정 관리                  │
│ • AuthorAccountController- 작가 계정 관리                   │
│ • AdminAccountController - 관리자 계정 관리                 │
└─────────────────────────────────────────────────────────────┘
```

#### Domain Layer (Core Business Logic)
```
┌─────────────────────────────────────────────────────────────┐
│                    Domain Layer                             │
├─────────────────────────────────────────────────────────────┤
│ • Entity                                                    │
│   ├── UserAccount      - 일반 사용자 엔티티                 │
│   ├── AuthorAccount    - 작가 엔티티                        │
│   └── AdminAccount     - 관리자 엔티티                      │
│                                                             │
│ • Command                                                    │
│   ├── SignupCommand    - 회원가입 명령                      │
│   ├── LoginCommand     - 로그인 명령                        │
│   └── LogoutCommand    - 로그아웃 명령                      │
│                                                             │
│ • Event                                                      │
│   ├── AuthorRegistrationRequested - 작가 등록 요청 이벤트   │
│   ├── Logged            - 로그인 이벤트                     │
│   └── Loggedout         - 로그아웃 이벤트                   │
│                                                             │
│ • Repository (Interface)                                     │
│   ├── UserAccountRepository                                │
│   ├── AuthorAccountRepository                              │
│   └── AdminAccountRepository                               │
└─────────────────────────────────────────────────────────────┘
```

#### Infrastructure Layer (Outbound Adapter)
```
┌─────────────────────────────────────────────────────────────┐
│                Infrastructure Layer                         │
├─────────────────────────────────────────────────────────────┤
│ • Security                                                    │
│   ├── JwtTokenUtil        - JWT 토큰 생성/검증              │
│   ├── JwtAuthenticationFilter - JWT 인증 필터               │
│   ├── JwtUserDetailsService - 사용자 상세 서비스            │
│   └── SecurityConfig       - Spring Security 설정           │
│                                                             │
│ • Repository (Implementation)                                │
│   ├── UserAccountRepositoryImpl                            │
│   ├── AuthorAccountRepositoryImpl                          │
│   └── AdminAccountRepositoryImpl                           │
│                                                             │
│ • Event Processing                                           │
│   ├── AbstractEvent        - 이벤트 추상 클래스             │
│   └── Kafka Integration    - Kafka 이벤트 발행              │
└─────────────────────────────────────────────────────────────┘
```

### 3. 데이터 모델

#### UserAccount
```java
@Entity
public class UserAccount {
    private Long id;
    private String email;
    private String password;        // BCrypt 암호화
    private UserRole roles;         // USER, AUTHOR, ADMIN
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

#### AuthorAccount
```java
@Entity
public class AuthorAccount {
    private Long id;
    private String email;
    private String password;        // BCrypt 암호화
    private UserRole roles;         // AUTHOR (고정)
    private String selfIntroduction;
    private String portfolio;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

#### AdminAccount
```java
@Entity
public class AdminAccount {
    private Long id;
    private String email;
    private String password;        // BCrypt 암호화
    private UserRole roles;         // ADMIN (고정)
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
```

### 4. 인증 플로우

#### 로그인 플로우
```
1. 클라이언트 요청
   POST /auth/login
   {
     "email": "user@example.com",
     "password": "password123"
   }

2. AuthenticationManager 검증
   ├── UserAccount 테이블 검색
   ├── AuthorAccount 테이블 검색
   └── AdminAccount 테이블 검색

3. JWT 토큰 생성
   {
     "sub": "userId",
     "iat": "발급시간",
     "exp": "만료시간",
     "role": "ROLE_USER"
   }

4. 응답 반환
   {
     "token": "JWT_TOKEN",
     "email": "user@example.com",
     "userId": 1,
     "role": "ROLE_USER"
   }
```

#### 인증 플로우
```
1. 요청 수신
   GET /userAccounts
   Authorization: Bearer JWT_TOKEN

2. JwtAuthenticationFilter
   ├── 토큰 추출
   ├── 토큰 검증
   ├── 사용자 정보 로드
   └── SecurityContext 설정

3. Controller 실행
   └── @PreAuthorize 권한 검증

4. 응답 반환
```

### 5. 이벤트 기반 통신

#### 작가 등록 이벤트 플로우
```
1. 작가 회원가입
   POST /authorAccounts/signup

2. 도메인 로직 실행
   ├── 이메일 중복 체크
   ├── 비밀번호 암호화
   ├── 계정 저장
   └── 이벤트 발행

3. AuthorRegistrationRequested 이벤트
   {
     "id": "작가ID",
     "email": "author@example.com",
     "selfIntroduction": "작가 소개",
     "portfolio": "포트폴리오 URL"
   }

4. Kafka로 이벤트 발행
   └── 다른 마이크로서비스에서 구독
```

### 6. 보안 아키텍처

#### Spring Security 설정
```java
@Configuration
public class SecurityConfig {
    // 허용된 경로
    .antMatchers("/auth/**", "/h2-console/**", 
                "/userAccounts/signup", "/authorAccounts/signup")
    .permitAll()
    
    // 나머지는 인증 필요
    .anyRequest().authenticated()
}
```

#### JWT 토큰 구조
```
Header.Payload.Signature

Payload:
{
  "sub": "userId",
  "iat": 1640995200,
  "exp": 1640998800,
  "role": "ROLE_USER"
}
```

### 7. API 설계 원칙

#### RESTful API 설계
- **회원가입**: `POST /{accountType}/signup`
- **로그인**: `POST /auth/login` (통합)
- **토큰 검증**: `POST /auth/validate`
- **계정 조회**: `GET /{accountType}`

#### 권한 기반 접근 제어
- **USER**: 일반 사용자 기능
- **AUTHOR**: 작가 전용 기능
- **ADMIN**: 관리자 전용 기능

### API 엔드포인트

#### 인증 관련
- `POST /auth/login` - 통합 로그인 (모든 사용자 타입)
- `POST /auth/signup` - 일반 사용자 회원가입
- `POST /auth/validate` - 토큰 검증

#### 계정 관리
- `POST /userAccounts/signup` - 일반 사용자 회원가입
- `POST /authorAccounts/signup` - 작가 회원가입
- `POST /adminAccounts/signup` - 관리자 계정 생성 (ADMIN 권한 필요)

#### 사용 예시

**로그인**
```bash
curl -X POST http://localhost:8085/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

**회원가입**
```bash
curl -X POST http://localhost:8085/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "email": "newuser@example.com",
    "password": "password123"
  }'
```

**작가 회원가입**
```bash
curl -X POST http://localhost:8085/authorAccounts/signup \
  -H "Content-Type: application/json" \
  -d '{
    "email": "author@example.com",
    "password": "password123",
    "selfIntroduction": "소설 작가입니다.",
    "portfolio": "https://example.com/portfolio"
  }'
```

**보호된 리소스 접근**
```bash
curl -X GET http://localhost:8085/userAccounts \
  -H "Authorization: Bearer YOUR_JWT_TOKEN"
```

### 역할 기반 접근 제어

- **USER**: 일반 사용자 권한
- **AUTHOR**: 작가 권한
- **ADMIN**: 관리자 권한

### 설정

`application.yml`에서 JWT 설정을 변경할 수 있습니다:

```yaml
jwt:
  secret: your-secret-key
  expiration: 86400  # 24시간 (초 단위)
```

## Running in local development environment

```
mvn spring-boot:run
```

## Packaging and Running in docker environment

```
mvn package -B -DskipTests
docker build -t username/authidentity:v1 .
docker run username/authidentity:v1
```

## Push images and running in Kubernetes

```
docker login 
# in case of docker hub, enter your username and password

docker push username/authidentity:v1
```

Edit the deployment.yaml under the /kubernetes directory:
```
    spec:
      containers:
        - name: authidentity
          image: username/authidentity:latest   # change this image name
          ports:
            - containerPort: 8080

```

Apply the yaml to the Kubernetes:
```
kubectl apply -f kubernetes/deployment.yaml
```

See the pod status:
```
kubectl get pods -l app=authidentity
```

If you have no problem, you can connect to the service by opening a proxy between your local and the kubernetes by using this command:
```
# new terminal
kubectl port-forward deploy/authidentity 8080:8080

# another terminal
http localhost:8080
```

If you have any problem on running the pod, you can find the reason by hitting this:
```
kubectl logs -l app=authidentity
```

Following problems may be occurred:

1. ImgPullBackOff:  Kubernetes failed to pull the image with the image name you've specified at the deployment.yaml. Please check your image name and ensure you have pushed the image properly.
1. CrashLoopBackOff: The spring application is not running properly. If you didn't provide the kafka installation on the kubernetes, the application may crash. Please install kafka firstly:

https://labs.msaez.io/#/courses/cna-full/full-course-cna/ops-utility

