package aivle.domain;

import aivle.BooksubstriptionmanagementApplication;
import aivle.domain.BookSubscribed;
import aivle.domain.월구독자도서열람됨;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "BookSubscription_table")
@Data
//<<< DDD / Aggregate Root
public class BookSubscription {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String name;

    private Long bookId;

    private Boolean isBookSubscribed;

    private Date updatedAt;

    @PostPersist
    public void onPostPersist() {
        BookSubscribed bookSubscribed = new BookSubscribed(this);
        bookSubscribed.publishAfterCommit();

        월구독자도서열람됨 월구독자도서열람됨 = new 월구독자도서열람됨(this);
        월구독자도서열람됨.publishAfterCommit();
    }

    public static BookSubscriptionRepository repository() {
        BookSubscriptionRepository bookSubscriptionRepository = BooksubstriptionmanagementApplication.applicationContext.getBean(
            BookSubscriptionRepository.class
        );
        return bookSubscriptionRepository;
    }

    //<<< Clean Arch / Port Method
    public static void subscriptionRequest(PointExpired pointExpired) {
        //implement business logic here:

        /** Example 1:  new item 
        BookSubscription bookSubscription = new BookSubscription();
        repository().save(bookSubscription);

        */

        /** Example 2:  finding and process
        

        repository().findById(pointExpired.get???()).ifPresent(bookSubscription->{
            
            bookSubscription // do something
            repository().save(bookSubscription);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
