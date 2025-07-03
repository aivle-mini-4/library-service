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
    public Subscribe unsubscribe(
            @RequestHeader(value = "X-User-Id", required = true) String userId,
            @RequestHeader(value = "X-User-Role", required = true) String userRole,
            @RequestBody UnsubscribeRequestCommand command) {
        
        if (!"ROLE_USER".equals(userRole)) {
            throw new RuntimeException("일반유저가 아닙니다");
        }

        try {
            Long id = Long.parseLong(userId);
            command.setId(id);
            return unsubscribeCommandHandler.handle(command);
        } catch (NumberFormatException e) {
            throw new RuntimeException("유효하지 않은 사용자 ID입니다");
        }
    }
}
//>>> Clean Arch / Inbound Adaptor
