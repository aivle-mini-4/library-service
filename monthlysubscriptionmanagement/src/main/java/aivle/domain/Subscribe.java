package aivle.domain;

import aivle.MonthlysubscriptionmanagementApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
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

    private String name;

    private Boolean isSubscribed;

    private Date updatedAt;

    public static SubscribeRepository repository() {
        SubscribeRepository subscribeRepository = MonthlysubscriptionmanagementApplication.applicationContext.getBean(
            SubscribeRepository.class
        );
        return subscribeRepository;
    }

    //<<< Clean Arch / Port Method
    public void subscribeRequest(
        SubscribeRequestCommand subscribeRequestCommand
    ) {
        //implement business logic here:

        Subscribed subscribed = new Subscribed(this);
        subscribed.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public void unsubscribeRequest(
        UnsubscribeRequestCommand unsubscribeRequestCommand
    ) {
        //implement business logic here:

        UnSubscribed unSubscribed = new UnSubscribed(this);
        unSubscribed.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
