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
        Subscribe subscribe = new Subscribe();
        subscribe.setName(command.getName());
        subscribe.subscribeRequest(command);
        return repository.save(subscribe);
    }
}

