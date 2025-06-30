# authidentity

## JWT 인증 시스템

이 서비스는 JWT 토큰 기반 인증/인가 시스템을 제공합니다.

### 주요 기능
- JWT 토큰 기반 인증
- 역할 기반 접근 제어 (RBAC)
- 비밀번호 암호화 (BCrypt)
- 토큰 검증 및 갱신

### API 엔드포인트

#### 인증 관련
- `POST /auth/login` - 로그인
- `POST /auth/signup` - 회원가입
- `POST /auth/validate` - 토큰 검증

#### 사용 예시

**로그인**
```bash
curl -X POST http://localhost:8080/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

**회원가입**
```bash
curl -X POST http://localhost:8080/auth/signup \
  -H "Content-Type: application/json" \
  -d '{
    "email": "newuser@example.com",
    "password": "password123"
  }'
```

**보호된 리소스 접근**
```bash
curl -X GET http://localhost:8080/userAccounts/1 \
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

