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
        @RequestParam("bookId") Long bookId,
        @RequestParam("price") Integer price
    ) throws Exception {
        System.out.println("##### /bookSubscription/subscribeBook called (bookId param) #####");

        SubscribeBookCommand subscribeBookCommand = new SubscribeBookCommand();
        subscribeBookCommand.setUserId(userId);
        subscribeBookCommand.setBookId(bookId);
        subscribeBookCommand.setPrice(price);

        Optional<BookSubscription> optionalBookSubscription = bookSubscriptionRepository.findByUserIdAndBookId(userId, bookId);

        BookSubscription bookSubscription;
        if (optionalBookSubscription.isPresent()) {
            bookSubscription = optionalBookSubscription.get();
            
            if (Boolean.FALSE.equals(bookSubscription.getIsBookSubscribed())) {
                bookSubscription.subscribeBook(subscribeBookCommand);
            } else {
                throw new IllegalStateException("이미 구독된 도서");
            }
        } else {
            
            bookSubscription = new BookSubscription();
            bookSubscription.subscribeBook(subscribeBookCommand);
        }

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
        @RequestParam("bookId") Long bookId,
        @RequestParam("price") Integer price

    ) throws Exception {
        System.out.println("##### /bookSubscription/viewBook called #####");

        ViewBookCommand viewBookCommand = new ViewBookCommand();

        viewBookCommand.setUserId(userId);
        viewBookCommand.setBookId(bookId);
        viewBookCommand.setPrice(price);

        Optional<BookSubscription> optionalBookSubscription = bookSubscriptionRepository.findByUserIdAndBookId(userId, bookId);

        BookSubscription bookSubscription;
        if (optionalBookSubscription.isPresent()) {
            bookSubscription = optionalBookSubscription.get();
            
            if (Boolean.FALSE.equals(bookSubscription.getIsBookSubscribed())) {
                bookSubscription.viewBook(viewBookCommand);
            } else {
                throw new IllegalStateException("이미 구독된 도서");
            }
        } else {
            
            bookSubscription = new BookSubscription();
            bookSubscription.viewBook(viewBookCommand);
        }

        bookSubscriptionRepository.save(bookSubscription);

        return bookSubscription;
    }

}
//>>> Clean Arch / Inbound Adaptor
