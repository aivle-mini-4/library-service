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
// @RequestMapping(value="/bookSubscriptions")
@Transactional
public class BookSubscriptionController {

    @Autowired
    BookSubscriptionRepository bookSubscriptionRepository;

    @RequestMapping(
        value = "/bookSubscriptions/subscribebook",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )

    @PostMapping("/bookSubscriptions/subscribebook")
    public BookSubscription subscribeBook(
        @RequestHeader("X-User-Id") Long userId,
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody SubscribeBookCommand subscribeBookCommand

    ) throws Exception {
        System.out.println("##### /bookSubscription/subscribeBook called #####");
        
        subscribeBookCommand.setUserId(userId);

        BookSubscription bookSubscription = new BookSubscription();
        bookSubscription.subscribeBook(subscribeBookCommand);
        bookSubscriptionRepository.save(bookSubscription);

        return bookSubscription;
    }


    @RequestMapping(
        value = "/bookSubscriptions/viewbook",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )

    @PostMapping("/bookSubscriptions/viewbook")
    public BookSubscription viewBook(
        @RequestHeader("X-User-Id") Long userId,

        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody ViewBookCommand viewBookCommand

    ) throws Exception {
        System.out.println("##### /bookSubscription/viewBook called #####");

        viewBookCommand.setUserId(userId);
        
        BookSubscription bookSubscription = new BookSubscription();
        
        bookSubscription.viewBook(viewBookCommand);
        bookSubscriptionRepository.save(bookSubscription);
        
        return bookSubscription;
    }
}
//>>> Clean Arch / Inbound Adaptor
