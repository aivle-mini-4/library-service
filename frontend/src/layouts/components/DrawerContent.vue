<script setup>
import {
  VerticalNavLink,
  VerticalNavSectionTitle,
} from '@layouts'
</script>

<template>
    <ul>
        <VerticalNavSectionTitle :item="{ heading: '관리자 작업' }" />
        <VerticalNavLink
            :item="{
                title: '작가승인',
                to: '/authorapprovals',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '포인트정책',
                to: '/pointpolicies',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '포인트정책조회',
                to: '/pointPolicyViews',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '월구독관리' }" />
        <VerticalNavLink
            :item="{
                title: '구독',
                to: '/subscribes',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '구독조회',
                to: '/subscribeViews',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '사용자 정보 관리' }" />
        <VerticalNavLink
            :item="{
                title: '작가정보',
                to: '/writerProfiles',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '회원정보',
                to: '/memberProfiles',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '관리자정보',
                to: '/adminProfiles',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '작가정보조회',
                to: '/writerPages',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '관리자정보조회',
                to: '/adminPages',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '회원정보조회',
                to: '/userPages',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '인가/인증' }" />
        <VerticalNavLink
            :item="{
                title: '회원계정',
                to: '/userAccounts',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '작가계정',
                to: '/authorAccounts',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '관리자계정',
                to: '/adminAccounts',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '포인트 관리' }" />
        <VerticalNavLink
            :item="{
                title: '포인트',
                to: '/points',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '포인트 조회',
                to: '/pointViews',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '원고 관리' }" />
        <VerticalNavLink
            :item="{
                title: '원고',
                to: '/manuscripts',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '원고조회',
                to: '/manuscriptPages',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '유저 이력 관리' }" />
        <VerticalNavLink
            :item="{
                title: '열람이력',
                to: '/viewHistories',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '즐겨찾기',
                to: '/favorites',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '열람이력',
                to: '/queryViewHistories',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '즐겨찾기목록조회',
                to: '/queryFavoriteLists',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: 'AI서비스' }" />
        <VerticalNavLink
            :item="{
                title: 'AI 서비스',
                to: '/aiServices',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '도서관리' }" />
        <VerticalNavLink
            :item="{
                title: '도서',
                to: '/books',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '베스트셀러 등록 조회',
                to: '/registeredBestsellerViews',
            }"
        />
        <VerticalNavLink
            :item="{
                title: '등록 도서 조회',
                to: '/registeredBookViews',
            }"
        />
        <VerticalNavSectionTitle :item="{ heading: '도서구독관리' }" />
        <VerticalNavLink
            :item="{
                title: '도서구독',
                to: '/bookSubscriptions',
            }"
        />
    </ul>
</template>
