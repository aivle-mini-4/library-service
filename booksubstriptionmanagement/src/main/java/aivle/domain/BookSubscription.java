package aivle.domain;

import aivle.BooksubstriptionmanagementApplication;
import aivle.domain.BookSubscribed;
import aivle.domain.MonthlyBookSubscribed;
import aivle.domain.SubscriptionRequested;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
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

    private Long bookId;
    
    private Long userId;
    
    private Integer price;

    private String bookName;;

    private Boolean isBookSubscribed;

    private LocalDateTime updatedAt;

    @PostPersist
    public void onPostPersist() {
        BookSubscribed bookSubscribed = new BookSubscribed(this);
        bookSubscribed.publishAfterCommit();

        MonthlyBookSubscribed monthlyBookSubscribed = new MonthlyBookSubscribed(this);
        monthlyBookSubscribed.publishAfterCommit();
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
        //finding and process
        repository().findById(pointExpired.getUserId()).ifPresent(bookSubscription->{
            if (pointExpired.getPoints() == 0){
            
                SubscriptionRequested subscriptionRequested = new SubscriptionRequested(bookSubscription);
                subscriptionRequested.setUserId(pointExpired.getUserId());
                ubscriptionRequested.publishAfterCommit();
                
                repository().save(bookSubscription);
            }
         });
    }
    //>>> Clean Arch / Port Method
}
//>>> DDD / Aggregate Root
