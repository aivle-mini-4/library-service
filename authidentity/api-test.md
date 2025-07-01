# JWT 인증 API 테스트 가이드 (httpie)

## h2 db접속
http://localhost:8085/h2-console
Driver Class:	org.h2.Driver
JDBC URL: jdbc:h2:mem:testdb
Username: sa  
Password: (비어있음)

## 기본 API 테스트

### 1. 일반 사용자 회원가입

```bash
# 일반 사용자 회원가입 (JWT 토큰 반환)
http POST localhost:8085/auth/signup \
  email=user@example.com \
  password=password123
```

**예상 응답:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "email": "user@example.com",
  "userId": 1,
  "role": "ROLE_USER",
  "message": "User registered successfully"
}
```

### 2. 통합 로그인 (모든 사용자 타입)

```bash
# 사용자/작가/관리자 로그인 (통합 API)
http POST localhost:8085/auth/login \
  email=user@example.com \
  password=password123
```

**예상 응답:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9...",
  "email": "user@example.com",
  "userId": 1,
  "role": "ROLE_USER"
}
```

### 3. 토큰 검증

```bash
# 토큰 유효성 검증
http POST localhost:8085/auth/validate \
  Authorization:"Bearer YOUR_JWT_TOKEN_HERE"
```

**예상 응답:**
```json
{
  "valid": true,
  "userId": "1",
  "email": "user@example.com",
  "role": "ROLE_USER"
}
```

### 4. 작가 회원가입

```bash
# 작가 회원가입 (권한 불필요)
# 이 API는 작가회원가입 + 등록요청 이벤트 publish를 수행합니다
http POST localhost:8085/authorAccounts/signup \
  email=author@example.com \
  password=password123 \
  selfIntroduction="저는 소설 작가입니다." \
  portfolio=https://example.com/portfolio
```

**예상 응답:**
```json
{
  "id": 2,
  "email": "author@example.com",
  "roles": "AUTHOR",
  "selfIntroduction": "저는 소설 작가입니다.",
  "portfolio": "https://example.com/portfolio",
  "createdAt": "2024-01-01T00:00:00",
  "updatedAt": "2024-01-01T00:00:00"
}
```

**기능:**
- 작가 계정 생성 (비밀번호 암호화 포함)
- 이메일 중복 체크
- AuthorRegistrationRequested 이벤트 publish
- 자동으로 AUTHOR 역할 부여

### 5. 관리자 계정 생성 (H2 콘솔에서 직접)

```sql
-- H2 콘솔에서 실행
INSERT INTO AdminAccount_table (id, email, password, roles, created_at, updated_at) 
VALUES (1, 'admin@example.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'ADMIN', NOW(), NOW());
```

**참고**: 관리자 계정은 H2 콘솔에서 직접 생성하거나, 기존 관리자가 `/adminAccounts/signup` API를 사용하여 생성할 수 있습니다.

## 전체 테스트 시나리오

### 시나리오 1: 일반 사용자 플로우

```bash
# 1. 회원가입
TOKEN=$(http POST localhost:8085/auth/signup email=testuser@example.com password=password123 | jq -r '.token')
echo "Received token: $TOKEN"

# 2. 토큰 검증
http POST localhost:8085/auth/validate Authorization:"Bearer $TOKEN"

# 3. 보호된 리소스 접근
http GET localhost:8085/userAccounts Authorization:"Bearer $TOKEN"
```

### 시나리오 2: 작가 플로우

```bash
# 1. 작가 회원가입
http POST localhost:8085/authorAccounts/signup \
  email=author@example.com \
  password=password123 \
  selfIntroduction="소설 작가입니다." \
  portfolio=https://example.com/portfolio

# 2. 작가 로그인 (통합 API 사용)
AUTHOR_TOKEN=$(http POST localhost:8085/auth/login email=author@example.com password=password123 | jq -r '.token')

# 3. 작가 권한 확인
http POST localhost:8085/auth/validate Authorization:"Bearer $AUTHOR_TOKEN"
```

### 시나리오 3: 관리자 플로우

```bash
# 1. 관리자 로그인 (H2에서 생성한 계정)
ADMIN_TOKEN=$(http POST localhost:8085/auth/login email=admin@example.com password=password | jq -r '.token')

# 2. 관리자 권한으로 사용자 계정 조회
http GET localhost:8085/userAccounts Authorization:"Bearer $ADMIN_TOKEN"

# 3. 관리자 권한으로 작가 계정 조회
http GET localhost:8085/authorAccounts Authorization:"Bearer $ADMIN_TOKEN"
```

## 에러 케이스 테스트

### 1. 잘못된 비밀번호로 로그인

```bash
http POST localhost:8085/auth/login \
  email=user@example.com \
  password=wrongpassword
```

**예상 응답:**
```json
{
  "error": "Invalid credentials"
}
```

### 2. 토큰 없이 보호된 리소스 접근

```bash
http GET localhost:8085/userAccounts/1
```

**예상 응답:**
```json
{
  "error": "Unauthorized"
}
```

### 3. 만료된 토큰 사용

```bash
http GET localhost:8085/userAccounts/1 \
  Authorization:"Bearer EXPIRED_TOKEN_HERE"
```

**예상 응답:**
```json
{
  "error": "Token expired"
}
```

### 4. 권한 없는 리소스 접근

```bash
# USER 권한으로 ADMIN 전용 API 접근
http POST localhost:8085/adminAccounts/signup \
  Authorization:"Bearer USER_TOKEN_HERE" \
  email=admin@example.com \
  password=admin123
```

**예상 응답:**
```json
{
  "error": "Access denied"
}
```

### 5. 이메일 중복 회원가입

```bash
http POST localhost:8085/auth/signup \
  email=existing@example.com \
  password=password123
```

**예상 응답:**
```json
"Email already exists"
```

## 편의 기능

### 토큰 저장 및 재사용

```bash
# 토큰을 환경변수로 저장
export JWT_TOKEN=$(http POST localhost:8085/auth/login email=user@example.com password=password123 | jq -r '.token')

# 저장된 토큰 사용
http GET localhost:8085/userAccounts Authorization:"Bearer $JWT_TOKEN"
```

### JSON 응답 포맷팅

```bash
# jq를 사용한 응답 포맷팅
http POST localhost:8085/auth/login email=user@example.com password=password123 | jq '.'
```

### 헤더 확인

```bash
# 응답 헤더 포함하여 요청
http -v GET localhost:8085/userAccounts Authorization:"Bearer $JWT_TOKEN"
```

## 역할별 권한 테스트

### USER 권한
```bash
# 사용자 계정 조회
http GET localhost:8085/userAccounts/1 Authorization:"Bearer $USER_TOKEN"

# 작가 회원가입 (작가회원가입 + 등록요청 이벤트 publish)
http POST localhost:8085/authorAccounts/signup \
  email=author@example.com \
  password=password123 \
  selfIntroduction="작가 소개" \
  portfolio=https://example.com/portfolio
```

### AUTHOR 권한
```bash
# 작가 계정 조회
http GET localhost:8085/authorAccounts/1 Authorization:"Bearer $AUTHOR_TOKEN"

# 작가 회원가입 (이미 작가인 경우에도 가능)
http POST localhost:8085/authorAccounts/signup \
  email=author2@example.com \
  password=password123 \
  selfIntroduction="작가 소개" \
  portfolio=https://example.com/portfolio
```

### ADMIN 권한
```bash
# 모든 계정 조회
http GET localhost:8085/userAccounts Authorization:"Bearer $ADMIN_TOKEN"
http GET localhost:8085/authorAccounts Authorization:"Bearer $ADMIN_TOKEN"
http GET localhost:8085/adminAccounts Authorization:"Bearer $ADMIN_TOKEN"

# 관리자 계정 생성 (기존 관리자만 가능)
http POST localhost:8085/adminAccounts/signup \
  Authorization:"Bearer $ADMIN_TOKEN" \
  email=newadmin@example.com \
  password=admin123
```

## 성능 테스트

### 동시 요청 테스트

```bash
# 여러 사용자 동시 로그인 테스트
for i in {1..10}; do
  http POST localhost:8085/auth/login email=user$i@example.com password=password123 &
done
wait
```

### 토큰 검증 성능 테스트

```bash
# 토큰 검증 반복 테스트
for i in {1..100}; do
  http POST localhost:8085/auth/validate Authorization:"Bearer $JWT_TOKEN" > /dev/null
done
```

## 디버깅 팁

### 상세 로그 확인

```bash
# -v 옵션으로 상세 로그 확인
http -v POST localhost:8085/auth/login email=user@example.com password=password123
```

### 응답 시간 확인

```bash
# 응답 시간 측정
time http POST localhost:8085/auth/login email=user@example.com password=password123
```

### 헤더만 확인

```bash
# 헤더만 확인
http -h GET localhost:8085/userAccounts Authorization:"Bearer $JWT_TOKEN"
```

## 주의사항

1. **토큰 보안**: 토큰을 로그나 터미널 히스토리에 남기지 않도록 주의
2. **비밀번호**: 테스트용 비밀번호는 실제 환경에서 사용하지 않기
3. **환경변수**: 프로덕션 환경에서는 환경변수로 토큰 관리
4. **HTTPS**: 프로덕션 환경에서는 반드시 HTTPS 사용
5. **포트**: 서버는 8085 포트에서 실행됨

## 문제 해결

### 연결 오류
```bash
# 서버 상태 확인
http GET localhost:8085/actuator/health
```

### 인증 오류
```bash
# 토큰 재발급
TOKEN=$(http POST localhost:8085/auth/login email=user@example.com password=password123 | jq -r '.token')
```

### 권한 오류
```bash
# 현재 사용자 정보 확인
http POST localhost:8085/auth/validate Authorization:"Bearer $JWT_TOKEN"
```

## API 엔드포인트 요약

### 공개 엔드포인트 (인증 불필요)
- `POST /auth/login` - 통합 로그인 (모든 사용자 타입)
- `POST /auth/signup` - 일반 사용자 회원가입
- `POST /auth/validate` - 토큰 검증
- `POST /authorAccounts/signup` - 작가 회원가입

### 보호된 엔드포인트 (인증 필요)
- `GET /userAccounts` - 사용자 계정 조회
- `GET /authorAccounts` - 작가 계정 조회
- `GET /adminAccounts` - 관리자 계정 조회
- `POST /adminAccounts/signup` - 관리자 계정 생성 (ADMIN 권한 필요)
