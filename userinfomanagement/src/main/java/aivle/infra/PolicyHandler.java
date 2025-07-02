package aivle.infra;

import aivle.config.kafka.KafkaProcessor;
import aivle.domain.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.JsonNode;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    WriterProfileRepository writerProfileRepository;

    @Autowired
    MemberProfileRepository memberProfileRepository;

    @Autowired
    AdminProfileRepository adminProfileRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}



    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='CreateWriterProfile'"
    )
    public void wheneverCreateWriterProfile_CreateWriterProfile(
        @Payload CreateWriterProfile createWriterProfile
    ) {
        CreateWriterProfile event = createWriterProfile;
        System.out.println(
            "\n\n##### listener CreateWriterProfile : " +
            createWriterProfile +
            "\n\n"
        );

        // Sample Logic //
        WriterProfile.createWriterProfile(event);
    }



    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SignedUp' && headers['aggregateType']=='UserAccount'"
    )
    public void wheneverUserAccountSignedUp_CreateMemberProfile(
        @Payload SignedUp signedUp
    ) {
        SignedUp event = signedUp;
        System.out.println(
            "\n\n##### listener CreateMemberProfile : " + signedUp + "\n\n"
        );

        // Sample Logic //
        MemberProfile.createMemberProfile(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='SignedUp' && headers['aggregateType']=='AdminAccount'"
    )
    public void wheneverAdminAccountSignedUp_CreateAdminProfile(
        @Payload SignedUp signedUp
    ) {
        SignedUp event = signedUp;
        System.out.println(
            "\n\n##### listener CreateAdminProfile : " + signedUp + "\n\n"
        );

        // Sample Logic //
        AdminProfile.createAdminProfile(event);
    }

    @StreamListener(
        value = KafkaProcessor.INPUT,
        condition = "headers['type']=='AuthorApproved'"
    )
    public void wheneverAuthorApproved_CreateWriterProfile(
        @Payload Object authorApproved
    ) {
        System.out.println(
            "\n\n##### listener AuthorApproved : " +
            authorApproved +
            "\n\n"
        );

        // JSON 문자열을 파싱하여 authorId 추출
        try {
            // ObjectMapper를 사용하여 JSON 파싱
            com.fasterxml.jackson.databind.ObjectMapper mapper = new com.fasterxml.jackson.databind.ObjectMapper();
            com.fasterxml.jackson.databind.JsonNode jsonNode = mapper.readTree(authorApproved.toString());
            
            Long authorId = jsonNode.get("authorId").asLong();
            
            // AuthorApproved 이벤트를 CreateWriterProfile 이벤트로 변환
            CreateWriterProfile createWriterProfile = new CreateWriterProfile();
            
            // authorId 설정 (id는 자동 생성됨)
            createWriterProfile.setAuthorId(authorId);
            createWriterProfile.setName("작가_" + authorId);
            createWriterProfile.setEmail("author" + authorId + "@example.com");
            createWriterProfile.setRoles("WRITER");
            createWriterProfile.setBasicInformation("작가 기본 정보");
            createWriterProfile.setSelfIntroduction("작가 자기소개");
            createWriterProfile.setPortfolio("작가 포트폴리오");
            
            // Sample Logic //
            WriterProfile.createWriterProfile(createWriterProfile);
            
        } catch (Exception e) {
            System.err.println("Error parsing AuthorApproved event: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
//>>> Clean Arch / Inbound Adaptor
