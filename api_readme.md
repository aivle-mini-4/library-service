# 마이크로서비스 API 명세서

## 1. Auth Identity Service (인증 서비스)

### 1.1 로그인
- **URL**: `POST /auth/login`
- **Description**: 사용자 로그인 및 JWT 토큰 발급
- **Request Body**:
```json
{
  "email": "string",
  "password": "string"
}
```
- **Response**:
```json
{
  "token": "string",
  "email": "string", 
  "userId": "number",
  "role": "string"
}
```

### 1.2 토큰 검증
- **URL**: `POST /auth/validate`
- **Description**: JWT 토큰 유효성 검증
- **Headers**: `Authorization: Bearer {token}`
- **Response**:
```json
{
  "valid": "boolean",
  "userId": "number",
  "email": "string",
  "role": "string"
}
```

### 1.3 일반 사용자 회원가입
- **URL**: `POST /userAccounts/signup`
- **Description**: 일반 사용자 회원가입
- **Request Body**:
```json
{
  "email": "string",
  "password": "string"
}
```
- **Response**:
```json
{
  "id": "number",
  "email": "string",
  "password": "string",
  "roles": "string",
  "createdAt": "string",
  "updatedAt": "string"
}
```

### 1.4 작가 회원가입
- **URL**: `POST /authorAccounts/signup`
- **Description**: 작가 회원가입
- **Request Body**:
```json
{
  "email": "string",
  "password": "string",
  "selfIntroduction": "string",
  "portfolio": "string"
}
```
- **Response**:
```json
{
  "id": "number",
  "email": "string",
  "password": "string",
  "roles": "string",
  "selfIntroduction": "string",
  "portfolio": "string",
  "createdAt": "string",
  "updatedAt": "string"
}
```

### 1.5 관리자 회원가입
- **URL**: `POST /adminAccounts/signup`
- **Description**: 관리자 회원가입
- **Request Body**:
```json
{
  "email": "string",
  "password": "string"
}
```
- **Response**:
```json
{
  "id": "number",
  "email": "string",
  "password": "string",
  "roles": "string",
  "createdAt": "string",
  "updatedAt": "string"
}
```

## 2. Script Management Service (원고 관리 서비스)

### 2.1 원고 생성
- **URL**: `POST /manuscripts`
- **Description**: 새로운 원고 생성
- **Request Body**:
```json
{
  "authorId": "number",
  "title": "string",
  "content": "string"
}
```
- **Response**:
```json
{
  "id": "number",
  "authorId": "number",
  "title": "string",
  "content": "string"
}
```

### 2.2 원고 수정
- **URL**: `PUT /manuscripts/{id}`
- **Description**: 기존 원고 수정
- **Path Parameters**: `id` (number)
- **Request Body**:
```json
{
  "authorId": "number",
  "title": "string",
  "content": "string"
}
```
- **Response**:
```json
{
  "id": "number",
  "authorId": "number",
  "title": "string",
  "content": "string"
}
```

### 2.3 원고 삭제
- **URL**: `DELETE /manuscripts/{id}`
- **Description**: 원고 삭제
- **Path Parameters**: `id` (number)
- **Response**: 204 No Content

### 2.4 출판 요청
- **URL**: `POST /manuscripts/{id}`
- **Description**: 원고 출판 요청
- **Path Parameters**: `id` (number)
- **Response**: 200 OK

### 2.5 전체 원고 목록 조회
- **URL**: `GET /manuscripts`
- **Description**: 전체 원고 페이지 목록 조회
- **Response**:
```json
[
  {
    "id": "number",
    "authorId": "number",
    "title": "string",
    "content": "string"
  }
]
```

### 2.6 특정 원고 조회
- **URL**: `GET /manuscripts/{id}`
- **Description**: 특정 원고 페이지 조회
- **Path Parameters**: `id` (number)
- **Response**:
```json
{
  "id": "number",
  "authorId": "number",
  "title": "string",
  "content": "string"
}
```

## 3. Admin Task Service (관리자 작업 서비스)

### 3.1 작가 승인 요청 생성
- **URL**: `POST /authorapprovals`
- **Description**: 작가 승인 요청 생성
- **Request Body**:
```json
{
  "authorId": "number"
}
```
- **Response**:
```json
{
  "id": "number",
  "authorId": "number",
  "state": "string",
  "appliedAt": "string",
  "approvedAt": "string",
  "rejectedAt": "string",
  "adminId": "number",
  "reason": "string"
}
```

### 3.2 전체 승인 요청 목록 조회
- **URL**: `GET /authorapprovals`
- **Description**: 전체 작가 승인 요청 목록 조회
- **Response**:
```json
[
  {
    "id": "number",
    "authorId": "number",
    "state": "string",
    "appliedAt": "string",
    "approvedAt": "string",
    "rejectedAt": "string",
    "adminId": "number",
    "reason": "string"
  }
]
```

### 3.3 특정 승인 요청 조회
- **URL**: `GET /authorapprovals/{id}`
- **Description**: 특정 작가 승인 요청 조회
- **Path Parameters**: `id` (number)
- **Response**:
```json
{
  "id": "number",
  "authorId": "number",
  "state": "string",
  "appliedAt": "string",
  "approvedAt": "string",
  "rejectedAt": "string",
  "adminId": "number",
  "reason": "string"
}
```

### 3.4 승인 처리
- **URL**: `PUT /authorapprovals/{id}/approve`
- **Description**: 작가 승인 처리
- **Path Parameters**: `id` (number)
- **Query Parameters**: `adminId` (number)
- **Response**:
```json
{
  "id": "number",
  "authorId": "number",
  "state": "string",
  "appliedAt": "string",
  "approvedAt": "string",
  "rejectedAt": "string",
  "adminId": "number",
  "reason": "string"
}
```

### 3.5 거부 처리
- **URL**: `PUT /authorapprovals/{id}/reject`
- **Description**: 작가 승인 거부 처리
- **Path Parameters**: `id` (number)
- **Query Parameters**: 
  - `adminId` (number)
  - `reason` (string)
- **Response**:
```json
{
  "id": "number",
  "authorId": "number",
  "state": "string",
  "appliedAt": "string",
  "approvedAt": "string",
  "rejectedAt": "string",
  "adminId": "number",
  "reason": "string"
}
```

### 3.6 포인트 정책 생성
- **URL**: `POST /pointpolicies`
- **Description**: 포인트 정책 생성
- **Request Body**:
```json
{
  "name": "string",
  "description": "string",
  "pointType": "string",
  "amount": "number",
  "isActive": "boolean"
}
```
- **Response**:
```json
{
  "id": "number",
  "name": "string",
  "description": "string",
  "pointType": "string",
  "amount": "number",
  "isActive": "boolean",
  "createdAt": "string",
  "updatedAt": "string"
}
```

### 3.7 전체 포인트 정책 목록 조회
- **URL**: `GET /pointpolicies`
- **Description**: 전체 포인트 정책 목록 조회
- **Response**:
```json
[
  {
    "id": "number",
    "name": "string",
    "description": "string",
    "pointType": "string",
    "amount": "number",
    "isActive": "boolean",
    "createdAt": "string",
    "updatedAt": "string"
  }
]
```

### 3.8 특정 포인트 정책 조회
- **URL**: `GET /pointpolicies/{id}`
- **Description**: 특정 포인트 정책 조회
- **Path Parameters**: `id` (number)
- **Response**:
```json
{
  "id": "number",
  "name": "string",
  "description": "string",
  "pointType": "string",
  "amount": "number",
  "isActive": "boolean",
  "createdAt": "string",
  "updatedAt": "string"
}
```

### 3.9 포인트 정책 수정
- **URL**: `PUT /pointpolicies/{id}`
- **Description**: 포인트 정책 수정
- **Path Parameters**: `id` (number)
- **Request Body**:
```json
{
  "name": "string",
  "description": "string",
  "pointType": "string",
  "amount": "number",
  "isActive": "boolean"
}
```
- **Response**:
```json
{
  "id": "number",
  "name": "string",
  "description": "string",
  "pointType": "string",
  "amount": "number",
  "isActive": "boolean",
  "createdAt": "string",
  "updatedAt": "string"
}
```

### 3.10 포인트 정책 삭제
- **URL**: `DELETE /pointpolicies/{id}`
- **Description**: 포인트 정책 삭제
- **Path Parameters**: `id` (number)
- **Response**: 204 No Content

## 4. Monthly Subscription Management Service (월 구독 관리 서비스)

### 4.1 구독 요청
- **URL**: `POST /subscribes/subscriberequest`
- **Description**: 월 구독 요청
- **Request Body**:
```json
{
  "id": "number",
  "userId": "number",
  "name": "string",
  "isSubscribed": "boolean",
  "updatedAt": "string"
}
```
- **Response**:
```json
{
  "id": "number",
  "userId": "number",
  "name": "string",
  "isSubscribed": "boolean",
  "updatedAt": "string"
}
```

### 4.2 구독 해지 요청
- **URL**: `DELETE /subscribes/{id}/unsubscriberequest`
- **Description**: 월 구독 해지 요청
- **Path Parameters**: `id` (number)
- **Headers**: 
  - `X-User-Id`: "string"
  - `X-User-Role`: "string"
- **Request Body**:
```json
{
  "id": "number",
  "userId": "number",
  "name": "string",
  "isSubscribed": "boolean"
}
```
- **Response**:
```json
{
  "id": "number",
  "userId": "number",
  "name": "string",
  "isSubscribed": "boolean",
  "updatedAt": "string"
}
```

### 4.3 구독 조회
- **URL**: `GET /subscribesViews`
- **Description**: 월 구독 해지 요청
- **Response**:
```json
{
  "id": "number",
  "name": "string",
  "isSubscribed": "boolean", 
  "updatedAt": "string"
}
```

## 5. Book Subscription Management Service (도서 구독 관리 서비스)

### 5.1 비구독자도서 열람 신청
- **URL**: `POST /bookSubscriptions/subscribebook`
- **Description**: 비구독자도서열람신청
- **Request Body**:
```json
{
  "userId": "number",
  "isBookSubscribed": "boolean"
}
```
- **Response**:
```json
{
  "id": "number",
  "bookId": "number",
  "userId": "number",
  "price": "number",
  "bookName": "string",
  "isBookSubscribed": "boolean",
  "updatedAt": "string"
}
```

### 5.2 월구독자도서열람
- **URL**: `POST /bookSubscriptions/viewbook`
- **Description**: 월구독자도서열람
- **Request Body**:
```json
{
  "userId": "number",
  "isBookSubscribed": "boolean"
}
```
- **Response**:
```json
{
  "id": "number",
  "bookId": "number",
  "userId": "number",
  "price": "number",
  "bookName": "string",
  "isBookSubscribed": "boolean",
  "updatedAt": "string"
}
```

## 6. Books Management Service (도서 관리 서비스)

### 6.1 도서 삭제
- **URL**: `DELETE /books/{id}`
- **Description**: 도서 삭제
- **Path Parameters**: `id` (number)
- **Response**:
```json
{
  "isSuccess": "boolean",
  "message": "string",
  "result": null
}
```

### 6.2 전체 도서 목록 조회
- **URL**: `GET /books`
- **Description**: 전체 도서 목록 조회
- **Response**:
```json
{
  "isSuccess": "boolean",
  "message": "string",
  "result": [
    {
      "bookId": "number",
      "authorId": "number",
      "title": "string",
      "content": "string",
      "category": "string",
      "summary": "string",
      "coverImageUrl": "string",
      "price": "number",
      "views": "number",
      "createdAt": "string",
      "updatedAt": "string"
    }
  ]
}
```

### 6.3 특정 도서 조회
- **URL**: `GET /books/{bookId}`
- **Description**: 특정 도서 조회
- **Path Parameters**: `bookId` (number)
- **Response**:
```json
{
  "isSuccess": "boolean",
  "message": "string",
  "result": {
    "bookId": "number",
    "authorId": "number",
    "title": "string",
    "content": "string",
    "category": "string",
    "summary": "string",
    "coverImageUrl": "string",
    "price": "number",
    "views": "number",
    "createdAt": "string",
    "updatedAt": "string"
  }
}
```

### 6.4 전체 베스트셀러 목록 조회
- **URL**: `GET /bestsellers`
- **Description**: 전체 베스트셀러 목록 조회
- **Response**:
```json
{
  "isSuccess": "boolean",
  "message": "string",
  "result": [
    {
      "bookId": "number",
      "authorId": "number",
      "title": "string",
      "category": "string",
      "coverImageUrl": "string",
      "price": "number",
      "views": "number",
      "createdAt": "string",
      "updatedAt": "string"
    }
  ]
}
```

### 6.5 특정 베스트셀러 조회
- **URL**: `GET /bestsellers/{bookId}`
- **Description**: 특정 베스트셀러 조회
- **Path Parameters**: `bookId` (number)
- **Response**:
```json
{
  "isSuccess": "boolean",
  "message": "string",
  "result": {
    "bookId": "number",
    "authorId": "number",
    "title": "string",
    "category": "string",
    "coverImageUrl": "string",
    "price": "number",
    "views": "number",
    "createdAt": "string",
    "updatedAt": "string"
  }
}
```

## 7. User Info Management Service (사용자 정보 관리 서비스)

### 7.1 회원 프로필 수정
- **URL**: `PUT /memberProfiles/{id}`
- **Description**: 회원 프로필 수정
- **Path Parameters**: `id` (number)
- **Request Body**:
```json
{
  "name": "string",
  "email": "string",
  "roles": "string",
  "basicInformation": "string",
  "selfIntroduction": "string"
}
```
- **Response**:
```json
{
  "id": "number",
  "userId": "number",
  "name": "string",
  "email": "string",
  "roles": "string",
  "basicInformation": "string",
  "selfIntroduction": "string",
  "updatedAt": "string"
}
```

### 7.2 작가 프로필 수정
- **URL**: `PUT /writerProfiles/{id}`
- **Description**: 작가 프로필 수정
- **Path Parameters**: `id` (number)
- **Request Body**:
```json
{
  "name": "string",
  "email": "string",
  "roles": "string",
  "basicInformation": "string",
  "selfIntroduction": "string",
  "portfolio": "string"
}
```
- **Response**:
```json
{
  "id": "number",
  "authorId": "number",
  "name": "string",
  "email": "string",
  "roles": "string",
  "basicInformation": "string",
  "selfIntroduction": "string",
  "portfolio": "string",
  "updatedAt": "string"
}
```

## 8. AI Service (AI 서비스)

AI 서비스는 Kafka 이벤트 기반으로 동작하며, REST API 엔드포인트는 제공하지 않습니다. PublicationRequested 이벤트를 수신하여 자동으로 도서 출판 처리를 수행합니다.

## 9. Point Management Service (포인트 관리 서비스)

Point Management Service는 Spring Data REST를 통해 자동으로 REST API가 생성됩니다.

### 9.1 포인트 정보 조회
- **URL**: `GET /points`
- **Description**: 전체 포인트 정보 목록 조회
- **Response**:
```json
{
  "_embedded": {
    "points": [
      {
        "id": "number",
        "userId": "number",
        "points": "number",
        "history": "string",
        "_links": {
          "self": {
            "href": "string"
          },
          "point": {
            "href": "string"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "string"
    },
    "profile": {
      "href": "string"
    }
  },
  "page": {
    "size": "number",
    "totalElements": "number",
    "totalPages": "number",
    "number": "number"
  }
}
```

### 9.2 특정 사용자 포인트 조회
- **URL**: `GET /points/{id}`
- **Description**: 특정 사용자의 포인트 정보 조회
- **Path Parameters**: `id` (number)
- **Response**:
```json
{
  "id": "number",
  "userId": "number",
  "points": "number",
  "history": "string",
  "_links": {
    "self": {
      "href": "string"
    },
    "point": {
      "href": "string"
    }
  }
}
```

### 9.3 포인트 뷰 정보 조회
- **URL**: `GET /pointViews`
- **Description**: 전체 포인트 뷰 정보 목록 조회
- **Response**:
```json
{
  "_embedded": {
    "pointViews": [
      {
        "id": "number",
        "userId": "number",
        "points": "number",
        "history": "string",
        "_links": {
          "self": {
            "href": "string"
          },
          "pointView": {
            "href": "string"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "string"
    },
    "profile": {
      "href": "string"
    }
  },
  "page": {
    "size": "number",
    "totalElements": "number",
    "totalPages": "number",
    "number": "number"
  }
}
```

### 9.4 특정 사용자 포인트 뷰 조회
- **URL**: `GET /pointViews/{id}`
- **Description**: 특정 사용자의 포인트 뷰 정보 조회
- **Path Parameters**: `id` (number)
- **Response**:
```json
{
  "id": "number",
  "userId": "number",
  "points": "number",
  "history": "string",
  "_links": {
    "self": {
      "href": "string"
    },
    "pointView": {
      "href": "string"
    }
  }
}
```

### 9.5 사용자별 포인트 뷰 조회
- **URL**: `GET /pointViews/search/findByUserId?userId={userId}`
- **Description**: 특정 사용자 ID로 포인트 뷰 정보 조회
- **Query Parameters**: `userId` (number)
- **Response**:
```json
{
  "_embedded": {
    "pointViews": [
      {
        "id": "number",
        "userId": "number",
        "points": "number",
        "history": "string",
        "_links": {
          "self": {
            "href": "string"
          },
          "pointView": {
            "href": "string"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "string"
    },
    "profile": {
      "href": "string"
    }
  }
}
```

## 10. User History Management Service (사용자 히스토리 관리 서비스)

User History Management Service는 Spring Data REST를 통해 자동으로 REST API가 생성됩니다.

### 10.1 즐겨찾기 목록 조회
- **URL**: `GET /favorites`
- **Description**: 전체 즐겨찾기 목록 조회
- **Response**:
```json
{
  "_embedded": {
    "favorites": [
      {
        "id": "number",
        "bookId": "number",
        "userId": "number",
        "_links": {
          "self": {
            "href": "string"
          },
          "favorite": {
            "href": "string"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "string"
    },
    "profile": {
      "href": "string"
    }
  },
  "page": {
    "size": "number",
    "totalElements": "number",
    "totalPages": "number",
    "number": "number"
  }
}
```

### 10.2 특정 즐겨찾기 조회
- **URL**: `GET /favorites/{id}`
- **Description**: 특정 즐겨찾기 정보 조회
- **Path Parameters**: `id` (number)
- **Response**:
```json
{
  "id": "number",
  "bookId": "number",
  "userId": "number",
  "_links": {
    "self": {
      "href": "string"
    },
    "favorite": {
      "href": "string"
    }
  }
}
```

### 10.3 조회 히스토리 목록 조회
- **URL**: `GET /viewHistories`
- **Description**: 전체 조회 히스토리 목록 조회
- **Response**:
```json
{
  "_embedded": {
    "viewHistories": [
      {
        "id": "number",
        "bookId": "number",
        "userId": "number",
        "_links": {
          "self": {
            "href": "string"
          },
          "viewHistory": {
            "href": "string"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "string"
    },
    "profile": {
      "href": "string"
    }
  },
  "page": {
    "size": "number",
    "totalElements": "number",
    "totalPages": "number",
    "number": "number"
  }
}
```

### 10.4 특정 조회 히스토리 조회
- **URL**: `GET /viewHistories/{id}`
- **Description**: 특정 조회 히스토리 정보 조회
- **Path Parameters**: `id` (number)
- **Response**:
```json
{
  "id": "number",
  "bookId": "number",
  "userId": "number",
  "_links": {
    "self": {
      "href": "string"
    },
    "viewHistory": {
      "href": "string"
    }
  }
}
```

### 10.5 쿼리 즐겨찾기 목록 조회
- **URL**: `GET /queryFavoriteLists`
- **Description**: 쿼리용 즐겨찾기 목록 조회
- **Response**:
```json
{
  "_embedded": {
    "queryFavoriteLists": [
      {
        "id": "number",
        "bookId": "number",
        "userId": "number",
        "_links": {
          "self": {
            "href": "string"
          },
          "queryFavoriteList": {
            "href": "string"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "string"
    },
    "profile": {
      "href": "string"
    }
  },
  "page": {
    "size": "number",
    "totalElements": "number",
    "totalPages": "number",
    "number": "number"
  }
}
```

### 10.6 특정 쿼리 즐겨찾기 조회
- **URL**: `GET /queryFavoriteLists/{id}`
- **Description**: 특정 쿼리 즐겨찾기 정보 조회
- **Path Parameters**: `id` (number)
- **Response**:
```json
{
  "id": "number",
  "bookId": "number",
  "userId": "number",
  "_links": {
    "self": {
      "href": "string"
    },
    "queryFavoriteList": {
      "href": "string"
    }
  }
}
```

### 10.7 쿼리 조회 히스토리 목록 조회
- **URL**: `GET /queryViewHistories`
- **Description**: 쿼리용 조회 히스토리 목록 조회
- **Response**:
```json
{
  "_embedded": {
    "queryViewHistories": [
      {
        "id": "number",
        "bookId": "number",
        "userId": "number",
        "_links": {
          "self": {
            "href": "string"
          },
          "queryViewHistory": {
            "href": "string"
          }
        }
      }
    ]
  },
  "_links": {
    "self": {
      "href": "string"
    },
    "profile": {
      "href": "string"
    }
  },
  "page": {
    "size": "number",
    "totalElements": "number",
    "totalPages": "number",
    "number": "number"
  }
}
```

### 10.8 특정 쿼리 조회 히스토리 조회
- **URL**: `GET /queryViewHistories/{id}`
- **Description**: 특정 쿼리 조회 히스토리 정보 조회
- **Path Parameters**: `id` (number)
- **Response**:
```json
{
  "id": "number",
  "bookId": "number",
  "userId": "number",
  "_links": {
    "self": {
      "href": "string"
    },
    "queryViewHistory": {
      "href": "string"
    }
  }
}
```

## 공통 응답 형식

### 성공 응답
```json
{
  "isSuccess": true,
  "message": "성공입니다.",
  "result": {
    // 실제 데이터
  }
}
```

### 에러 응답
```json
{
  "isSuccess": false,
  "message": "에러 메시지",
  "result": null
}
```

## 인증

대부분의 API는 JWT 토큰 인증이 필요합니다. Authorization 헤더에 Bearer 토큰을 포함해야 합니다:
```
Authorization: Bearer {jwt_token}
```

## 서비스 포트 정보

- **Gateway**: 8080
- **Admin Task**: 8082
- **Monthly Subscription Management**: 8083
- **User Info Management**: 8084
- **Auth Identity**: 8085
- **Point Management**: 8086
- **Script Management**: 8087
- **User History Management**: 8088
- **AI Service**: 8089
- **Books Management**: 8090
- **Book Subscription Management**: 8091
- **Frontend**: 8080 