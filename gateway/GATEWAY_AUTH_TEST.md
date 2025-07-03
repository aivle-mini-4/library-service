## 테스트한 시나리오
어드민 회원가입 > 로그인 > 포인트확인
http -v POST localhost:8092/adminAccounts/signup \
  email=admin@example.com \
  password=admin123


http -v POST localhost:8092/auth/login \
  email=admin@example.com \
  password=admin123


http -v GET http://localhost:8092/pointpolicies \
  Authorization:"Bearer {token}"

### 유저 안 들어가는지 확인
http -v POST localhost:8092/userAccounts/signup \
  email=user@example.com \
  password=password123

http POST localhost:8092/auth/login \
  email=user@example.com \
  password=password123

http -v GET http://localhost:8092/pointpolicies \
  Authorization:"Bearer {token}"

### 포인트정책생성
http -v POST http://localhost:8092/pointpolicies \
  Authorization:"Bearer {token}" \
  name="신규 가입 보너스" \
  description="신규 회원 가입 시 지급되는 포인트" \
  pointType="ACCUMULATION" \
  amount=1000 \
  isActive=true

### 포인트정책 조회
http -v GET http://localhost:8092/pointpolicies \
  Authorization:"Bearer {token}"

### 특정 포인트정책 조회
http -v GET http://localhost:8092/pointpolicies/1 \
  Authorization:"Bearer {token}"

### 포인트정책 수정
http -v PUT http://localhost:8092/pointpolicies/1 \
  Authorization:"Bearer {token}" \
  name="수정된 신규 가입 보너스" \
  description="수정된 신규 회원 가입 시 지급되는 포인트" \
  pointType="ACCUMULATION" \
  amount=1500 \
  isActive=true

### 포인트정책 삭제
http -v DELETE http://localhost:8092/pointpolicies/1 \
  Authorization:"Bearer {token}"
