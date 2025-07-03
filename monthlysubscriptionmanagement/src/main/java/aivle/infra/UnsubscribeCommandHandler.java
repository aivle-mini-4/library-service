package aivle.infra;

import aivle.domain.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class UnsubscribeCommandHandler {

    @Autowired
    private SubscribeRepository repository;

    @Transactional
    public Subscribe handle(UnsubscribeRequestCommand command) {
        Subscribe subscribe = repository.findById(command.getId())
                .orElseThrow(() -> new RuntimeException("구독 정보 없음"));

        if (!subscribe.getIsSubscribed()) {
            throw new RuntimeException("이미 구독 해지된 상태입니다.");
        }

        subscribe.unsubscribeRequest(command);
        return repository.save(subscribe);
    }
}

