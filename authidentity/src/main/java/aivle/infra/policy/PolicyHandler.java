package aivle.infra.policy;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.stream.annotation.StreamListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Service;

import aivle.config.kafka.KafkaProcessor;
import aivle.domain.repository.AdminAccountRepository;
import aivle.domain.repository.AuthorAccountRepository;
import aivle.domain.repository.UserAccountRepository;

//<<< Clean Arch / Inbound Adaptor
@Service
@Transactional
public class PolicyHandler {

    @Autowired
    UserAccountRepository userAccountRepository;

    @Autowired
    AuthorAccountRepository authorAccountRepository;

    @Autowired
    AdminAccountRepository adminAccountRepository;

    @StreamListener(KafkaProcessor.INPUT)
    public void whatever(@Payload String eventString) {}
}
//>>> Clean Arch / Inbound Adaptor
