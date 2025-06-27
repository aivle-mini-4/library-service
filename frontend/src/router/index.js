import { createRouter, createWebHashHistory } from 'vue-router';

const router = createRouter({
  history: createWebHashHistory(),
  routes: [
    {
      path: '/',
      component: () => import('../components/pages/Index.vue'),
    },
    {
      path: '/authorapprovals',
      component: () => import('../components/ui/AuthorapprovalGrid.vue'),
    },
    {
      path: '/pointpolicies',
      component: () => import('../components/ui/PointpolicyGrid.vue'),
    },
    {
      path: '/pointPolicyViews',
      component: () => import('../components/PointPolicyViewView.vue'),
    },
    {
      path: '/subscribes',
      component: () => import('../components/ui/SubscribeGrid.vue'),
    },
    {
      path: '/subscribeViews',
      component: () => import('../components/SubscribeViewView.vue'),
    },
    {
      path: '/writerProfiles',
      component: () => import('../components/ui/WriterProfileGrid.vue'),
    },
    {
      path: '/memberProfiles',
      component: () => import('../components/ui/MemberProfileGrid.vue'),
    },
    {
      path: '/adminProfiles',
      component: () => import('../components/ui/AdminProfileGrid.vue'),
    },
    {
      path: '/writerPages',
      component: () => import('../components/WriterPageView.vue'),
    },
    {
      path: '/adminPages',
      component: () => import('../components/AdminPageView.vue'),
    },
    {
      path: '/userPages',
      component: () => import('../components/UserPageView.vue'),
    },
    {
      path: '/userAccounts',
      component: () => import('../components/ui/UserAccountGrid.vue'),
    },
    {
      path: '/authorAccounts',
      component: () => import('../components/ui/AuthorAccountGrid.vue'),
    },
    {
      path: '/adminAccounts',
      component: () => import('../components/ui/AdminAccountGrid.vue'),
    },
    {
      path: '/points',
      component: () => import('../components/ui/PointGrid.vue'),
    },
    {
      path: '/pointViews',
      component: () => import('../components/PointViewView.vue'),
    },
    {
      path: '/manuscripts',
      component: () => import('../components/ui/ManuscriptGrid.vue'),
    },
    {
      path: '/manuscriptPages',
      component: () => import('../components/ManuscriptPageView.vue'),
    },
    {
      path: '/viewHistories',
      component: () => import('../components/ui/ViewHistoryGrid.vue'),
    },
    {
      path: '/favorites',
      component: () => import('../components/ui/FavoriteGrid.vue'),
    },
    {
      path: '/queryViewHistories',
      component: () => import('../components/QueryViewHistoryView.vue'),
    },
    {
      path: '/queryFavoriteLists',
      component: () => import('../components/QueryFavoriteListView.vue'),
    },
    {
      path: '/aiServices',
      component: () => import('../components/ui/AiServiceGrid.vue'),
    },
    {
      path: '/books',
      component: () => import('../components/ui/BooksGrid.vue'),
    },
    {
      path: '/registeredBestsellerViews',
      component: () => import('../components/RegisteredBestsellerViewView.vue'),
    },
    {
      path: '/registeredBookViews',
      component: () => import('../components/RegisteredBookViewView.vue'),
    },
    {
      path: '/bookSubscriptions',
      component: () => import('../components/ui/BookSubscriptionGrid.vue'),
    },
  ],
})

export default router;
