package aivle.infra;

import aivle.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SubscribeCommandHandler {

    @Autowired
    private SubscribeRepository repository;

    @Transactional
    public Subscribe handle(SubscribeRequestCommand command) {
        // 이미 구독 중인지 확인
        Subscribe existing = repository.findByUserId(command.getUserId());
        if (existing != null && existing.getIsSubscribed()) {
            throw new RuntimeException("이미 구독 중입니다.");
        }

        Subscribe subscribe = (existing != null) ? existing : new Subscribe();
        subscribe.setUserId(command.getUserId());
        subscribe.subscribeRequest(command);
        return repository.save(subscribe);
    }
}

