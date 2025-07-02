## 테스트한 시나리오
어드민 회원가입 > 로그인 > 포인트확인
http -v POST localhost:8085/adminAccounts/signup \  email=admin@example.com \
  password=admin123


http -v POST localhost:8085/auth/login \
  email=admin@example.com \
  password=admin123


http -v GET http://localhost:8092/pointpolicies \
  Authorization:"Bearer {token}"