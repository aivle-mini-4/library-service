package aivle.domain;

import aivle.MonthlysubscriptionmanagementApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Subscribe_table")
@Data
//<<< DDD / Aggregate Root
public class Subscribe {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private String name;

    private Boolean isSubscribed;

    private LocalDateTime updatedAt;

    public static SubscribeRepository repository() {
        return MonthlysubscriptionmanagementApplication.applicationContext.getBean(SubscribeRepository.class);
    }

    //<<< Clean Arch / Port Method
    public void subscribeRequest(
        SubscribeRequestCommand subscribeRequestCommand
    ) {
        //implement business logic here:
        this.isSubscribed = true;
        this.updatedAt = LocalDateTime.now();

        Subscribed subscribed = new Subscribed(this);
        subscribed.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void unsubscribeRequest(
        UnsubscribeRequestCommand unsubscribeRequestCommand
    ) {
        //implement business logic here:
        this.isSubscribed = false;
        this.updatedAt = LocalDateTime.now();

        UnSubscribed unSubscribed = new UnSubscribed(this);
        unSubscribed.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
