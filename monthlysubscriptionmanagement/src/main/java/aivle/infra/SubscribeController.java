package aivle.infra;

import aivle.domain.*;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/subscribes")
@Transactional
public class SubscribeController {

    @Autowired
    SubscribeRepository subscribeRepository;

    @RequestMapping(
        value = "/subscribes/subscriberequest",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public Subscribe subscribeRequest(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody SubscribeRequestCommand subscribeRequestCommand
    ) throws Exception {
        System.out.println("##### /subscribe/subscribeRequest  called #####");
        Subscribe subscribe = new Subscribe();
        subscribe.subscribeRequest(subscribeRequestCommand);
        subscribeRepository.save(subscribe);
        return subscribe;
    }

    @RequestMapping(
        value = "/subscribes/{id}/unsubscriberequest",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )
    public Subscribe unsubscribeRequest(
        @PathVariable(value = "id") Long id,
        @RequestBody UnsubscribeRequestCommand unsubscribeRequestCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /subscribe/unsubscribeRequest  called #####");
        Optional<Subscribe> optionalSubscribe = subscribeRepository.findById(
            id
        );

        optionalSubscribe.orElseThrow(() -> new Exception("No Entity Found"));
        Subscribe subscribe = optionalSubscribe.get();
        subscribe.unsubscribeRequest(unsubscribeRequestCommand);

        subscribeRepository.delete(subscribe);
        return subscribe;
    }
}
//>>> Clean Arch / Inbound Adaptor
