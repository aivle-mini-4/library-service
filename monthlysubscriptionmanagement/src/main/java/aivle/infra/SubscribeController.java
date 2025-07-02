package aivle.infra;

import aivle.domain.*;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping(value="/subscribes")
@Transactional
public class SubscribeController {

    @Autowired
    private SubscribeCommandHandler subscribeCommandHandler;

    @Autowired
    private UnsubscribeCommandHandler unsubscribeCommandHandler;

    @PostMapping("/subscriberequest")
    public Subscribe subscribe(@RequestBody SubscribeRequestCommand command) {
        return subscribeCommandHandler.handle(command);
    }

    @DeleteMapping("/{id}/unsubscriberequest")
    public Subscribe unsubscribe(@PathVariable Long id,
                                 @RequestBody UnsubscribeRequestCommand command) {
        command.setId(id);
        return unsubscribeCommandHandler.handle(command);
    }
}
//>>> Clean Arch / Inbound Adaptor
