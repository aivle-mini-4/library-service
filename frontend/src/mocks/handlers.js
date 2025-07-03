import { http, HttpResponse } from 'msw'

export const handlers = [
  // ===== Auth Identity Service =====

  // 로그인
  http.post('/auth/login', async ({ request }) => {
    const { email, password } = await request.json()

    if (!email || !password) {
      return HttpResponse.json(
        {
          isSuccess: false,
          message: '이메일과 비밀번호를 입력해주세요.',
          result: null,
        },
        { status: 400 },
      )
    }

    return HttpResponse.json({
      token: 'mock-jwt-token',
      email: email,
      userId: 1,
      role: 'USER',
    })
  }),

  // 토큰 검증
  http.post('/auth/validate', ({ request }) => {
    const authHeader = request.headers.get('Authorization')

    if (!authHeader || !authHeader.startsWith('Bearer ')) {
      return HttpResponse.json('Invalid token', { status: 400 })
    }

    return HttpResponse.json({
      valid: true,
      userId: 1,
      email: 'test@example.com',
      role: 'USER',
    })
  }),

  // 일반 사용자 회원가입
  http.post('/userAccounts/signup', async ({ request }) => {
    const { email, password } = await request.json()

    if (!email || !password) {
      return HttpResponse.json(
        {
          error: '이메일과 비밀번호를 입력해주세요.',
        },
        { status: 400 },
      )
    }

    return HttpResponse.json({
      id: 1,
      email: email,
      password: password,
      roles: 'USER',
      createdAt: '2025-01-01T00:00:00',
      updatedAt: '2025-01-01T00:00:00',
    })
  }),

  // 작가 회원가입
  http.post('/authorAccounts/signup', async ({ request }) => {
    const { email, password, selfIntroduction, portfolio } = await request.json()

    if (!email || !password) {
      return HttpResponse.json(
        {
          error: '이메일과 비밀번호를 입력해주세요.',
        },
        { status: 400 },
      )
    }

    return HttpResponse.json({
      id: 1,
      email: email,
      password: password,
      roles: 'AUTHOR',
      selfIntroduction: selfIntroduction,
      portfolio: portfolio,
      createdAt: '2025-01-01T00:00:00',
      updatedAt: '2025-01-01T00:00:00',
    })
  }),

  // 관리자 회원가입
  http.post('/adminAccounts/signup', async ({ request }) => {
    const { email, password } = await request.json()

    if (!email || !password) {
      return HttpResponse.json(
        {
          error: '이메일과 비밀번호를 입력해주세요.',
        },
        { status: 400 },
      )
    }

    return HttpResponse.json({
      id: 1,
      email: email,
      password: password,
      roles: 'ADMIN',
      createdAt: '2025-01-01T00:00:00',
      updatedAt: '2025-01-01T00:00:00',
    })
  }),

  // ===== Script Management Service =====

  // 원고 생성
  http.post('/manuscripts', async ({ request }) => {
    const { authorId, title, content } = await request.json()

    if (!authorId || !title || !content) {
      return HttpResponse.json(
        {
          error: '작가ID, 제목, 내용을 입력해주세요.',
        },
        { status: 400 },
      )
    }

    return HttpResponse.json({
      id: 1,
      authorId: authorId,
      title: title,
      content: content,
    })
  }),

  // 원고 수정
  http.put('/manuscripts/:id', async ({ params, request }) => {
    const { authorId, title, content } = await request.json()

    if (!authorId || !title || !content) {
      return HttpResponse.json(
        {
          error: '작가ID, 제목, 내용을 입력해주세요.',
        },
        { status: 400 },
      )
    }

    return HttpResponse.json({
      id: params.id,
      authorId: authorId,
      title: title,
      content: content,
    })
  }),

  // 원고 삭제
  http.delete('/manuscripts/:id', ({ params }) => {
    return new HttpResponse(null, { status: 204 })
  }),

  // 출판 요청
  http.post('/manuscripts/:id', ({ params }) => {
    return HttpResponse.json({ message: '출판 요청이 완료되었습니다.' })
  }),

  // 전체 원고 목록 조회
  http.get('/manuscripts', () => {
    return HttpResponse.json([
      {
        id: 1,
        authorId: 1,
        title: '원고 제목 1',
        content: '원고 내용 1',
      },
      {
        id: 2,
        authorId: 1,
        title: '원고 제목 2',
        content: '원고 내용 2',
      },
    ])
  }),

  // 특정 원고 조회
  http.get('/manuscripts/:id', ({ params }) => {
    return HttpResponse.json({
      id: params.id,
      authorId: 1,
      title: '원고 제목',
      content: '원고 내용',
    })
  }),

  // ===== Admin Task Service =====

  // 작가 승인 요청 생성
  http.post('/authorapprovals', async ({ request }) => {
    const { authorId } = await request.json()

    return HttpResponse.json({
      id: 1,
      authorId: authorId,
      state: 'PENDING',
      appliedAt: '2025-01-01T00:00:00',
      approvedAt: null,
      rejectedAt: null,
      adminId: null,
      reason: null,
    })
  }),

  // 전체 승인 요청 목록 조회
  http.get('/authorapprovals', () => {
    return HttpResponse.json([
      {
        id: 1,
        authorId: 1,
        state: 'PENDING',
        appliedAt: '2025-01-01T00:00:00',
        approvedAt: null,
        rejectedAt: null,
        adminId: null,
        reason: null,
      },
    ])
  }),

  // 특정 승인 요청 조회
  http.get('/authorapprovals/:id', ({ params }) => {
    return HttpResponse.json({
      id: params.id,
      authorId: 1,
      state: 'PENDING',
      appliedAt: '2025-01-01T00:00:00',
      approvedAt: null,
      rejectedAt: null,
      adminId: null,
      reason: null,
    })
  }),

  // 승인 처리
  http.put('/authorapprovals/:id/approve', ({ params, request }) => {
    const url = new URL(request.url)
    const adminId = url.searchParams.get('adminId')

    return HttpResponse.json({
      id: params.id,
      authorId: 1,
      state: 'APPROVED',
      appliedAt: '2025-01-01T00:00:00',
      approvedAt: '2025-01-01T00:00:00',
      rejectedAt: null,
      adminId: adminId,
      reason: null,
    })
  }),

  // 거부 처리
  http.put('/authorapprovals/:id/reject', ({ params, request }) => {
    const url = new URL(request.url)
    const adminId = url.searchParams.get('adminId')
    const reason = url.searchParams.get('reason')

    return HttpResponse.json({
      id: params.id,
      authorId: 1,
      state: 'REJECTED',
      appliedAt: '2025-01-01T00:00:00',
      approvedAt: null,
      rejectedAt: '2025-01-01T00:00:00',
      adminId: adminId,
      reason: reason,
    })
  }),

  // 포인트 정책 생성
  http.post('/pointpolicies', async ({ request }) => {
    const data = await request.json()

    return HttpResponse.json({
      id: 1,
      name: data.name,
      description: data.description,
      pointType: data.pointType,
      amount: data.amount,
      isActive: data.isActive,
      createdAt: '2025-01-01T00:00:00',
      updatedAt: '2025-01-01T00:00:00',
    })
  }),

  // 전체 포인트 정책 목록 조회
  http.get('/pointpolicies', () => {
    return HttpResponse.json([
      {
        id: 1,
        name: '기본포인트',
        description: '회원가입 시 지급되는 기본 포인트',
        pointType: 'ACCUMULATION',
        amount: 1000,
        isActive: true,
        createdAt: '2025-01-01T00:00:00',
        updatedAt: '2025-01-01T00:00:00',
      },
    ])
  }),

  // 특정 포인트 정책 조회
  http.get('/pointpolicies/:id', ({ params }) => {
    return HttpResponse.json({
      id: params.id,
      name: '기본포인트',
      description: '회원가입 시 지급되는 기본 포인트',
      pointType: 'ACCUMULATION',
      amount: 1000,
      isActive: true,
      createdAt: '2025-01-01T00:00:00',
      updatedAt: '2025-01-01T00:00:00',
    })
  }),

  // 포인트 정책 수정
  http.put('/pointpolicies/:id', async ({ params, request }) => {
    const data = await request.json()

    return HttpResponse.json({
      id: params.id,
      name: data.name,
      description: data.description,
      pointType: data.pointType,
      amount: data.amount,
      isActive: data.isActive,
      createdAt: '2025-01-01T00:00:00',
      updatedAt: '2025-01-01T00:00:00',
    })
  }),

  // 포인트 정책 삭제
  http.delete('/pointpolicies/:id', () => {
    return new HttpResponse(null, { status: 204 })
  }),

  // ===== Monthly Subscription Management Service =====

  // 구독 요청
  http.post('/subscribes/subscriberequest', async ({ request }) => {
    const data = await request.json()

    return HttpResponse.json({
      id: 1,
      userId: data.userId,
      name: data.name,
      isSubscribed: true,
      updatedAt: '2025-01-01T00:00:00',
    })
  }),

  // 구독 해지 요청
  http.delete('/subscribes/:id/unsubscriberequest', async ({ params, request }) => {
    const data = await request.json()

    return HttpResponse.json({
      id: params.id,
      userId: data.userId,
      name: data.name,
      isSubscribed: false,
      updatedAt: '2025-01-01T00:00:00',
    })
  }),

  // 구독 조회
  http.get('/subscribesViews', () => {
    return HttpResponse.json({
      id: 1,
      name: '구독자',
      isSubscribed: true,
      updatedAt: '2025-01-01T00:00:00',
    })
  }),

  // ===== Book Subscription Management Service =====

  // 도서 구독
  http.post('/bookSubscriptions/subscribebook', async ({ request }) => {
    const data = await request.json()

    return HttpResponse.json({
      id: 1,
      bookId: 1,
      userId: data.userId,
      price: 1000,
      bookName: '도서 제목',
      isBookSubscribed: data.isBookSubscribed,
      updatedAt: '2025-01-01T00:00:00',
    })
  }),

  // 도서 조회
  http.post('/bookSubscriptions/viewbook', async ({ request }) => {
    const data = await request.json()

    return HttpResponse.json({
      id: 1,
      bookId: 1,
      userId: data.userId,
      price: 1000,
      bookName: '도서 제목',
      isBookSubscribed: data.isBookSubscribed,
      updatedAt: '2025-01-01T00:00:00',
    })
  }),

  // ===== Books Management Service =====

  // 도서 삭제
  http.delete('/books/:id', () => {
    return HttpResponse.json({
      isSuccess: true,
      message: '성공입니다.',
      result: null,
    })
  }),

  // 전체 도서 목록 조회
  http.get('/books', () => {
    return HttpResponse.json({
      isSuccess: true,
      message: '성공입니다.',
      result: [
        {
          bookId: 1,
          authorId: 1,
          title: '도서 제목 1',
          content: '도서 내용 1',
          category: '소설',
          summary: '도서 요약 1',
          coverImageUrl: 'https://example.com/cover1.jpg',
          price: 1000,
          views: 100,
          createdAt: '2025-01-01T00:00:00',
          updatedAt: '2025-01-01T00:00:00',
        },
      ],
    })
  }),

  // 특정 도서 조회
  http.get('/books/:bookId', ({ params }) => {
    return HttpResponse.json({
      isSuccess: true,
      message: '성공입니다.',
      result: {
        bookId: params.bookId,
        authorId: 1,
        title: '도서 제목',
        content: '도서 내용',
        category: '소설',
        summary: '도서 요약',
        coverImageUrl: 'https://example.com/cover.jpg',
        price: 1000,
        views: 100,
        createdAt: '2025-01-01T00:00:00',
        updatedAt: '2025-01-01T00:00:00',
      },
    })
  }),

  // 전체 베스트셀러 목록 조회
  http.get('/bestsellers', () => {
    return HttpResponse.json({
      isSuccess: true,
      message: '성공입니다.',
      result: [
        {
          bookId: 1,
          authorId: 1,
          title: '베스트셀러 제목 1',
          category: '소설',
          coverImageUrl: 'https://example.com/cover1.jpg',
          price: 1000,
          views: 1000,
          createdAt: '2025-01-01T00:00:00',
          updatedAt: '2025-01-01T00:00:00',
        },
      ],
    })
  }),

  // 특정 베스트셀러 조회
  http.get('/bestsellers/:bookId', ({ params }) => {
    return HttpResponse.json({
      isSuccess: true,
      message: '성공입니다.',
      result: {
        bookId: params.bookId,
        authorId: 1,
        title: '베스트셀러 제목',
        category: '소설',
        coverImageUrl: 'https://example.com/cover.jpg',
        price: 1000,
        views: 1000,
        createdAt: '2025-01-01T00:00:00',
        updatedAt: '2025-01-01T00:00:00',
      },
    })
  }),

  // ===== User Info Management Service =====

  // 회원 프로필 수정
  http.put('/memberProfiles/:id', async ({ params, request }) => {
    const data = await request.json()

    return HttpResponse.json({
      id: params.id,
      userId: 1,
      name: data.name,
      email: data.email,
      roles: data.roles,
      basicInformation: data.basicInformation,
      selfIntroduction: data.selfIntroduction,
      updatedAt: '2025-01-01T00:00:00',
    })
  }),

  // 작가 프로필 수정
  http.put('/writerProfiles/:id', async ({ params, request }) => {
    const data = await request.json()

    return HttpResponse.json({
      id: params.id,
      authorId: 1,
      name: data.name,
      email: data.email,
      roles: data.roles,
      basicInformation: data.basicInformation,
      selfIntroduction: data.selfIntroduction,
      portfolio: data.portfolio,
      updatedAt: '2025-01-01T00:00:00',
    })
  }),

  // ===== Point Management Service (Spring Data REST) =====

  // 포인트 정보 조회
  http.get('/points', () => {
    return HttpResponse.json({
      _embedded: {
        points: [
          {
            id: 1,
            userId: 1,
            points: 1000,
            history: '2025-01-01T00:00:00',
            _links: {
              self: {
                href: 'http://localhost:8080/points/1',
              },
              point: {
                href: 'http://localhost:8080/points/1',
              },
            },
          },
        ],
      },
      _links: {
        self: {
          href: 'http://localhost:8080/points',
        },
        profile: {
          href: 'http://localhost:8080/profile/points',
        },
      },
      page: {
        size: 20,
        totalElements: 1,
        totalPages: 1,
        number: 0,
      },
    })
  }),

  // 특정 사용자 포인트 조회
  http.get('/points/:id', ({ params }) => {
    return HttpResponse.json({
      id: params.id,
      userId: 1,
      points: 1000,
      history: '2025-01-01T00:00:00',
      _links: {
        self: {
          href: `http://localhost:8080/points/${params.id}`,
        },
        point: {
          href: `http://localhost:8080/points/${params.id}`,
        },
      },
    })
  }),

  // 포인트 뷰 정보 조회
  http.get('/pointViews', () => {
    return HttpResponse.json({
      _embedded: {
        pointViews: [
          {
            id: 1,
            userId: 1,
            points: 1000,
            history: '2025-01-01T00:00:00',
            _links: {
              self: {
                href: 'http://localhost:8080/pointViews/1',
              },
              pointView: {
                href: 'http://localhost:8080/pointViews/1',
              },
            },
          },
        ],
      },
      _links: {
        self: {
          href: 'http://localhost:8080/pointViews',
        },
        profile: {
          href: 'http://localhost:8080/profile/pointViews',
        },
      },
      page: {
        size: 20,
        totalElements: 1,
        totalPages: 1,
        number: 0,
      },
    })
  }),

  // 특정 사용자 포인트 뷰 조회
  http.get('/pointViews/:id', ({ params }) => {
    return HttpResponse.json({
      id: params.id,
      userId: 1,
      points: 1000,
      history: '2025-01-01T00:00:00',
      _links: {
        self: {
          href: `http://localhost:8080/pointViews/${params.id}`,
        },
        pointView: {
          href: `http://localhost:8080/pointViews/${params.id}`,
        },
      },
    })
  }),

  // 사용자별 포인트 뷰 조회
  http.get('/pointViews/search/findByUserId', ({ request }) => {
    const url = new URL(request.url)
    const userId = url.searchParams.get('userId')

    return HttpResponse.json({
      _embedded: {
        pointViews: [
          {
            id: 1,
            userId: userId,
            points: 1000,
            history: '2025-01-01T00:00:00',
            _links: {
              self: {
                href: 'http://localhost:8080/pointViews/1',
              },
              pointView: {
                href: 'http://localhost:8080/pointViews/1',
              },
            },
          },
        ],
      },
      _links: {
        self: {
          href: 'http://localhost:8080/pointViews/search/findByUserId?userId=1',
        },
        profile: {
          href: 'http://localhost:8080/profile/pointViews',
        },
      },
    })
  }),

  // ===== User History Management Service (Spring Data REST) =====

  // 즐겨찾기 목록 조회
  http.get('/favorites', () => {
    return HttpResponse.json({
      _embedded: {
        favorites: [
          {
            id: 1,
            bookId: 1,
            userId: 1,
            _links: {
              self: {
                href: 'http://localhost:8080/favorites/1',
              },
              favorite: {
                href: 'http://localhost:8080/favorites/1',
              },
            },
          },
        ],
      },
      _links: {
        self: {
          href: 'http://localhost:8080/favorites',
        },
        profile: {
          href: 'http://localhost:8080/profile/favorites',
        },
      },
      page: {
        size: 20,
        totalElements: 1,
        totalPages: 1,
        number: 0,
      },
    })
  }),

  // 특정 즐겨찾기 조회
  http.get('/favorites/:id', ({ params }) => {
    return HttpResponse.json({
      id: params.id,
      bookId: 1,
      userId: 1,
      _links: {
        self: {
          href: `http://localhost:8080/favorites/${params.id}`,
        },
        favorite: {
          href: `http://localhost:8080/favorites/${params.id}`,
        },
      },
    })
  }),

  // 조회 히스토리 목록 조회
  http.get('/viewHistories', () => {
    return HttpResponse.json({
      _embedded: {
        viewHistories: [
          {
            id: 1,
            bookId: 1,
            userId: 1,
            _links: {
              self: {
                href: 'http://localhost:8080/viewHistories/1',
              },
              viewHistory: {
                href: 'http://localhost:8080/viewHistories/1',
              },
            },
          },
        ],
      },
      _links: {
        self: {
          href: 'http://localhost:8080/viewHistories',
        },
        profile: {
          href: 'http://localhost:8080/profile/viewHistories',
        },
      },
      page: {
        size: 20,
        totalElements: 1,
        totalPages: 1,
        number: 0,
      },
    })
  }),

  // 특정 조회 히스토리 조회
  http.get('/viewHistories/:id', ({ params }) => {
    return HttpResponse.json({
      id: params.id,
      bookId: 1,
      userId: 1,
      _links: {
        self: {
          href: `http://localhost:8080/viewHistories/${params.id}`,
        },
        viewHistory: {
          href: `http://localhost:8080/viewHistories/${params.id}`,
        },
      },
    })
  }),

  // 쿼리 즐겨찾기 목록 조회
  http.get('/queryFavoriteLists', () => {
    return HttpResponse.json({
      _embedded: {
        queryFavoriteLists: [
          {
            id: 1,
            bookId: 1,
            userId: 1,
            _links: {
              self: {
                href: 'http://localhost:8080/queryFavoriteLists/1',
              },
              queryFavoriteList: {
                href: 'http://localhost:8080/queryFavoriteLists/1',
              },
            },
          },
        ],
      },
      _links: {
        self: {
          href: 'http://localhost:8080/queryFavoriteLists',
        },
        profile: {
          href: 'http://localhost:8080/profile/queryFavoriteLists',
        },
      },
      page: {
        size: 20,
        totalElements: 1,
        totalPages: 1,
        number: 0,
      },
    })
  }),

  // 특정 쿼리 즐겨찾기 조회
  http.get('/queryFavoriteLists/:id', ({ params }) => {
    return HttpResponse.json({
      id: params.id,
      bookId: 1,
      userId: 1,
      _links: {
        self: {
          href: `http://localhost:8080/queryFavoriteLists/${params.id}`,
        },
        queryFavoriteList: {
          href: `http://localhost:8080/queryFavoriteLists/${params.id}`,
        },
      },
    })
  }),

  // 쿼리 조회 히스토리 목록 조회
  http.get('/queryViewHistories', () => {
    return HttpResponse.json({
      _embedded: {
        queryViewHistories: [
          {
            id: 1,
            bookId: 1,
            userId: 1,
            _links: {
              self: {
                href: 'http://localhost:8080/queryViewHistories/1',
              },
              queryViewHistory: {
                href: 'http://localhost:8080/queryViewHistories/1',
              },
            },
          },
        ],
      },
      _links: {
        self: {
          href: 'http://localhost:8080/queryViewHistories',
        },
        profile: {
          href: 'http://localhost:8080/profile/queryViewHistories',
        },
      },
      page: {
        size: 20,
        totalElements: 1,
        totalPages: 1,
        number: 0,
      },
    })
  }),

  // 특정 쿼리 조회 히스토리 조회
  http.get('/queryViewHistories/:id', ({ params }) => {
    return HttpResponse.json({
      id: params.id,
      bookId: 1,
      userId: 1,
      _links: {
        self: {
          href: `http://localhost:8080/queryViewHistories/${params.id}`,
        },
        queryViewHistory: {
          href: `http://localhost:8080/queryViewHistories/${params.id}`,
        },
      },
    })
  }),
]
