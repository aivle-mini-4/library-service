<template>
  <v-container fluid>
    <v-row>
      <v-card
          v-for="(card, index) in cards"
          :key="index"
          class="mx-auto"
          style="height:300px; width:300px; margin-bottom:20px;"
          outlined
      >
        <v-list-item>
          <v-icon style="margin-top: 30%; margin-left: 37.5%;" color="primary" size="64">mdi-apps</v-icon>
        </v-list-item>
        <v-card-actions>
          <v-btn
              class="mx-auto"
              outlined
              rounded
              :to="card.link"
              style="font-weight:500; font-size:20px; border:solid 2px; max-width:250px; overflow:hidden; margin-top: 10%;"
          >
              {{ card.text }}
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-row>
  </v-container>
</template>

<script>
export default {
  name: 'Index',
  data: () => ({
    cards: [
      {
          text: "작가승인",
          link: "/authorapprovals",
      },
      {
          text: "포인트정책",
          link: "/pointpolicies",
      },
      {
          text: "포인트정책조회",
          link: "/pointPolicyViews",
      },
      {
          text: "구독",
          link: "/subscribes",
      },
      {
          text: "구독조회",
          link: "/subscribeViews",
      },
      {
          text: "작가정보",
          link: "/writerProfiles",
      },
      {
          text: "회원정보",
          link: "/memberProfiles",
      },
      {
          text: "관리자정보",
          link: "/adminProfiles",
      },
      {
          text: "작가정보조회",
          link: "/writerPages",
      },
      {
          text: "관리자정보조회",
          link: "/adminPages",
      },
      {
          text: "회원정보조회",
          link: "/userPages",
      },
      {
          text: "회원계정",
          link: "/userAccounts",
      },
      {
          text: "작가계정",
          link: "/authorAccounts",
      },
      {
          text: "관리자계정",
          link: "/adminAccounts",
      },
      {
          text: "포인트",
          link: "/points",
      },
      {
          text: "포인트 조회",
          link: "/pointViews",
      },
      {
          text: "원고",
          link: "/manuscripts",
      },
      {
          text: "원고조회",
          link: "/manuscriptPages",
      },
      {
          text: "열람이력",
          link: "/viewHistories",
      },
      {
          text: "즐겨찾기",
          link: "/favorites",
      },
      {
          text: "열람이력",
          link: "/queryViewHistories",
      },
      {
          text: "즐겨찾기목록조회",
          link: "/queryFavoriteLists",
      },
      {
          text: "AI 서비스",
          link: "/aiServices",
      },
      {
          text: "도서",
          link: "/books",
      },
      {
          text: "베스트셀러 등록 조회",
          link: "/registeredBestsellerViews",
      },
      {
          text: "등록 도서 조회",
          link: "/registeredBookViews",
      },
      {
          text: "도서구독",
          link: "/bookSubscriptions",
      },
    ],
  }),
};
</script>
