# Gateway 인증 테스트 가이드

## 1. 사전 준비

### 1.1 서비스 실행 확인
```bash
# 모든 서비스가 실행 중인지 확인
docker-compose ps
# 또는
kubectl get pods
```

### 1.2 Gateway 서비스 상태 확인
```bash
# Gateway 서비스 상태 확인
curl http://localhost:8088/actuator/health
```

## 2. 인증 테스트 시나리오

### 2.1 공개 경로 테스트 (인증 불필요)
```bash
# 로그인 API - 인증 없이 접근 가능해야 함
curl -X POST http://localhost:8088/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }'

# 회원가입 API - 인증 없이 접근 가능해야 함
curl -X POST http://localhost:8088/userAccounts/signup \
  -H "Content-Type: application/json" \
  -d '{
    "email": "newuser@example.com",
    "password": "password123"
  }'
```

### 2.2 보호된 경로 테스트 (인증 필요)

#### 2.2.1 인증 없이 접근 시도
```bash
# 인증 없이 보호된 경로 접근
curl http://localhost:8088/books/
# 예상 결과: 401 Unauthorized

curl http://localhost:8088/points/
# 예상 결과: 401 Unauthorized
```

#### 2.2.2 유효한 토큰으로 접근
```bash
# 1. 먼저 로그인해서 토큰 획득
LOGIN_RESPONSE=$(curl -s -X POST http://localhost:8088/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "test@example.com",
    "password": "password123"
  }')

# 2. 토큰 추출
TOKEN=$(echo $LOGIN_RESPONSE | jq -r '.token')

# 3. 토큰으로 보호된 경로 접근
curl http://localhost:8088/books/ \
  -H "Authorization: Bearer $TOKEN"

# 4. 헤더에 사용자 정보가 포함되었는지 확인
curl -v http://localhost:8088/books/ \
  -H "Authorization: Bearer $TOKEN"
```

#### 2.2.3 만료된 토큰으로 접근
```bash
# 만료된 토큰 사용
curl http://localhost:8088/books/ \
  -H "Authorization: Bearer expired_token_here"
# 예상 결과: 401 Unauthorized
```

#### 2.2.4 잘못된 형식의 토큰으로 접근
```bash
# Bearer 접두사 없이
curl http://localhost:8088/books/ \
  -H "Authorization: invalid_token"

# 빈 토큰
curl http://localhost:8088/books/ \
  -H "Authorization: Bearer "
```

## 3. 헤더 전달 확인

### 3.1 각 마이크로서비스에서 헤더 확인
```bash
# booksmanagement 서비스에서 헤더 확인
curl -v http://localhost:8088/books/ \
  -H "Authorization: Bearer $TOKEN"

# 응답에서 다음 헤더들이 전달되었는지 확인:
# X-User-Id: 사용자ID
# X-User-Role: 사용자역할
```

### 3.2 로그 확인
```bash
# Gateway 로그 확인
docker logs gateway-container-name

# 예상 로그 메시지:
# "인증된 사용자: userId=123, role=ROLE_USER"
# "Authorization 헤더 없음 또는 Bearer 토큰 아님"
# "access token 만료"
# "유효하지 않은 토큰"
```

## 4. 테스트 스크립트

### 4.1 전체 테스트 스크립트
```bash
#!/bin/bash

echo "=== Gateway 인증 테스트 시작 ==="

# 1. 공개 경로 테스트
echo "1. 공개 경로 테스트..."
curl -s -o /dev/null -w "%{http_code}" http://localhost:8088/auth/login
echo " - 로그인 페이지 접근"

# 2. 보호된 경로 테스트 (인증 없이)
echo "2. 보호된 경로 테스트 (인증 없이)..."
RESPONSE=$(curl -s -w "%{http_code}" http://localhost:8088/books/)
if [[ $RESPONSE == *"401"* ]]; then
    echo " ✓ 인증 없이 접근 차단됨"
else
    echo " ✗ 인증 없이 접근이 허용됨 (문제!)"
fi

# 3. 로그인 후 토큰 테스트
echo "3. 로그인 후 토큰 테스트..."
LOGIN_RESPONSE=$(curl -s -X POST http://localhost:8088/auth/login \
  -H "Content-Type: application/json" \
  -d '{"email": "test@example.com", "password": "password123"}')

TOKEN=$(echo $LOGIN_RESPONSE | jq -r '.token')

if [ "$TOKEN" != "null" ] && [ "$TOKEN" != "" ]; then
    echo " ✓ 토큰 발급 성공"
    
    # 토큰으로 보호된 경로 접근
    PROTECTED_RESPONSE=$(curl -s -w "%{http_code}" http://localhost:8088/books/ \
      -H "Authorization: Bearer $TOKEN")
    
    if [[ $PROTECTED_RESPONSE != *"401"* ]]; then
        echo " ✓ 인증된 사용자 접근 성공"
    else
        echo " ✗ 인증된 사용자 접근 실패"
    fi
else
    echo " ✗ 토큰 발급 실패"
fi

echo "=== 테스트 완료 ==="
```

## 5. 문제 해결

### 5.1 일반적인 문제들

#### Gateway가 시작되지 않는 경우
```bash
# 로그 확인
docker logs gateway-container-name

# 포트 충돌 확인
netstat -tulpn | grep 8088

# 설정 파일 문법 확인
# application.yml 파일의 YAML 문법 검증
```

#### 인증이 작동하지 않는 경우
```bash
# 1. JWT 시크릿 키 확인
# authidentity와 gateway의 jwt.secret이 동일한지 확인

# 2. 보호된 경로 설정 확인
# application.yml의 security.protected-paths 확인

# 3. 토큰 형식 확인
# Bearer 접두사가 올바른지 확인
```

#### 헤더가 전달되지 않는 경우
```bash
# 1. Gateway 로그에서 사용자 정보 추출 확인
# "인증된 사용자: userId=xxx, role=xxx" 로그 확인

# 2. 각 마이크로서비스에서 헤더 수신 확인
# X-User-Id, X-User-Role 헤더 확인
```

### 5.2 디버깅 명령어
```bash
# Gateway 상태 확인
curl -v http://localhost:8088/actuator/health

# 상세한 요청/응답 확인
curl -v http://localhost:8088/books/ \
  -H "Authorization: Bearer $TOKEN"

# 로그 레벨 변경 (application.yml)
logging:
  level:
    aivle.config: DEBUG
    org.springframework.cloud.gateway: DEBUG
```

## 6. 성능 테스트

### 6.1 부하 테스트
```bash
# Apache Bench를 사용한 부하 테스트
ab -n 1000 -c 10 -H "Authorization: Bearer $TOKEN" \
  http://localhost:8088/books/

# wrk를 사용한 부하 테스트
wrk -t12 -c400 -d30s -H "Authorization: Bearer $TOKEN" \
  http://localhost:8088/books/
```

## 7. 모니터링

### 7.1 메트릭 확인
```bash
# Gateway 메트릭 확인
curl http://localhost:8088/actuator/metrics

# 인증 관련 메트릭
curl http://localhost:8088/actuator/metrics/gateway.requests
```

이 가이드를 따라하면 Gateway 인증이 제대로 작동하는지 확인할 수 있어! 