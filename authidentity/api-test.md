# JWT 인증 API 테스트 가이드 (httpie)

## 기본 API 테스트

### 1. 회원가입 (Signup)

```bash
# 일반 사용자 회원가입
http POST localhost:8085/auth/signup \
  email=user@example.com \
  password=password123
```

**예상 응답:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE2MzQ1Njc4OTAsImV4cCI6MTYzNDY1NDI5MH0...",
  "email": "user@example.com",
  "role": "ROLE_USER"
}
```

### 2. 로그인 (Login)

```bash
# 사용자 로그인
http POST localhost:8085/auth/login \
  email=user@example.com \
  password=password123
```

**예상 응답:**
```json
{
  "token": "eyJhbGciOiJIUzUxMiJ9.eyJzdWIiOiJ1c2VyQGV4YW1wbGUuY29tIiwicm9sZSI6IlVTRVIiLCJpYXQiOjE2MzQ1Njc4OTAsImV4cCI6MTYzNDY1NDI5MH0...",
  "email": "user@example.com",
  "role": "ROLE_USER"
}
```

### 3. 토큰 검증 (Validate Token)

```bash
# 토큰 유효성 검증
http POST localhost:8085/auth/validate \
  Authorization:"Bearer YOUR_JWT_TOKEN_HERE"
```

**예상 응답:**
```json
{
  "valid": true,
  "email": "user@example.com",
  "role": "ROLE_USER"
}
```

### 4. 보호된 리소스 접근

```bash
# 사용자 계정 정보 조회 (인증 필요)
http GET localhost:8085/userAccounts/1 \
  Authorization:"Bearer YOUR_JWT_TOKEN_HERE"
```

### 5. 작가 등록 요청

```bash
# 작가 등록 요청 (USER 권한 필요)
http POST localhost:8085/authorAccounts/requestauthorregistration \
  Authorization:"Bearer YOUR_JWT_TOKEN_HERE" \
  email=author@example.com \
  password=password123 \
  selfIntroduction="저는 소설 작가입니다." \
  portfolio=https://example.com/portfolio
```

### 6. 관리자 계정 생성

```bash
# 관리자 계정 생성 (ADMIN 권한 필요)
http PUT localhost:8085/adminAccounts/1/signup \
  Authorization:"Bearer ADMIN_JWT_TOKEN_HERE" \
  email=admin@example.com \
  password=admin123
```

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

### 시나리오 2: 작가 등록 플로우

```bash
# 1. 사용자 로그인
TOKEN=$(http POST localhost:8085/auth/login email=testuser@example.com password=password123 | jq -r '.token')

# 2. 작가 등록 요청
http POST localhost:8085/authorAccounts/requestauthorregistration \
  Authorization:"Bearer $TOKEN" \
  email=author@example.com \
  password=password123 \
  selfIntroduction="소설 작가입니다." \
  portfolio=https://example.com/portfolio
```

### 시나리오 3: 관리자 플로우

```bash
# 1. 관리자 로그인 (관리자 계정이 이미 존재한다고 가정)
ADMIN_TOKEN=$(http POST localhost:8085/auth/login email=admin@example.com password=admin123 | jq -r '.token')

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
http PUT localhost:8085/adminAccounts/1/signup \
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

# 작가 등록 요청
http POST localhost:8085/authorAccounts/requestauthorregistration \
  Authorization:"Bearer $USER_TOKEN" \
  email=author@example.com \
  password=password123 \
  selfIntroduction="작가 소개" \
  portfolio=https://example.com/portfolio
```

### AUTHOR 권한
```bash
# 작가 계정 조회
http GET localhost:8085/authorAccounts/1 Authorization:"Bearer $AUTHOR_TOKEN"

# 작가 등록 요청 (이미 작가인 경우)
http POST localhost:8085/authorAccounts/requestauthorregistration \
  Authorization:"Bearer $AUTHOR_TOKEN" \
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

# 관리자 계정 생성
http PUT localhost:8085/adminAccounts/1/signup \
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