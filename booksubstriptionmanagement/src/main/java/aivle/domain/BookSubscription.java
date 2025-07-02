package aivle.domain;

import aivle.BooksubstriptionmanagementApplication;
import aivle.domain.BookSubscribed;
import aivle.domain.MonthlyBookSubscribed;
import aivle.domain.SubscriptionRequested;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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


    public static BookSubscriptionRepository repository() {
        BookSubscriptionRepository bookSubscriptionRepository = BooksubstriptionmanagementApplication.applicationContext.getBean(
            BookSubscriptionRepository.class
        );
        return bookSubscriptionRepository;
    }

    //<<< Clean Arch / Port Method
    // 비구독자
    public void subscribeBook(SubscribeBookCommand subscribeBookCommand) {
        if (Boolean.TRUE.equals(this.isBookSubscribed)) {
            throw new IllegalStateException("이미 구독된 도서");
        }
        
        this.userId = subscribeBookCommand.getUserId();
        this.isBookSubscribed = true;
        this.updatedAt = LocalDateTime.now();

        BookSubscribed bookSubscribed = new BookSubscribed(this);
        bookSubscribed.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method
    
    //<<< Clean Arch / Port Method
    // 구독자
    public void viewBook(ViewBookCommand viewBookCommand) {
        //implement business logic here:
        if (Boolean.TRUE.equals(this.isBookSubscribed)){
            throw new IllegalStateException("이미 조회된 도서");
        }

        this.userId = viewBookCommand.getUserId();
        this.isBookSubscribed = true;
        this.updatedAt = LocalDateTime.now();

        MonthlyBookSubscribed monthlyBookSubscribed = new MonthlyBookSubscribed(this);
        monthlyBookSubscribed.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void subscriptionRequest(PointExpired pointExpired) {
        //implement business logic here:
        //finding and process
        repository().findById(pointExpired.getUserId()).ifPresent(bookSubscription->{
            if (pointExpired.getPoints() == 0){
                

                SubscriptionRequested subscriptionRequested = new SubscriptionRequested(bookSubscription);
                subscriptionRequested.setUserId(pointExpired.getUserId());
                subscriptionRequested.publishAfterCommit();
                
                repository().save(bookSubscription);
            }
         });
    }
    //>>> Clean Arch / Port Method
}
//>>> DDD / Aggregate Root
