package aivle.infra;

import aivle.config.kafka.KafkaProcessor;
import aivle.domain.*;
import aivle.domain.Admin.AdminProfile;
import aivle.domain.Admin.AdminProfileRepository;
import aivle.domain.Author.AuthorProfile;
import aivle.domain.Author.AuthorProfileRepository;
import aivle.domain.Member.MemberProfile;
import aivle.domain.Member.MemberProfileRepository;

import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    AuthorProfileRepository authorProfileRepository;

    @Autowired
    MemberProfileRepository memberProfileRepository;

    @Autowired
    AdminProfileRepository adminProfileRepository;

  

    // member 회원가입 이벤트 처리 -> MemberProfile 생성
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SignedUp' && headers['aggregateType']=='UserAccount'"
    )
    public void wheneverUserAccountSignedUp_CreateMemberProfile(@Payload SignedUp signedUp) {
        MemberProfile.createMemberProfile(signedUp);
    }

    // admin 회원가입 이벤트 처리 -> AdminProfile 생성
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SignedUp' && headers['aggregateType']=='AdminAccount'"
    )
    public void wheneverAdminAccountSignedUp_CreateAdminProfile(@Payload SignedUp signedUp) {
        AdminProfile.createAdminProfile(signedUp);
    }

    // 작가 승인 이벤트 처리 -> AuthorProfile 생성
    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='AuthorApproved'"
    )
    public void wheneverAuthorApproved_CreateAuthorProfile(@Payload AuthorApproved authorApproved) {
        // ApprovalState가 APPROVED일 때만 작가 프로필 생성
        if (authorApproved.getState() != null && authorApproved.getState().equals(ApprovalState.APPROVED)) {
            AuthorProfile.createAuthorProfile(authorApproved);
        }
    }

}
//>>> Clean Arch / Inbound Adaptor
