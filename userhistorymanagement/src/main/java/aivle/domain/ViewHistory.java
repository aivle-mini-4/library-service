package aivle.domain;

import aivle.UserhistorymanagementApplication;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ViewHistory_table")
@Data
//<<< DDD / Aggregate Root
public class ViewHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Integer bookId;

    private String userId;

    public static ViewHistoryRepository repository() {
        ViewHistoryRepository viewHistoryRepository = UserhistorymanagementApplication.applicationContext.getBean(
            ViewHistoryRepository.class
        );
        return viewHistoryRepository;
    }

    //<<< Clean Arch / Port Method
    public void registerViewHistory(
        RegisterViewHistoryCommand registerViewHistoryCommand
    ) {
        //implement business logic here:

        ViewHistoryRegistered viewHistoryRegistered = new ViewHistoryRegistered(
            this
        );
        viewHistoryRegistered.publishAfterCommit();
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void registerViewHistory(
        월구독자도서열람됨 월구독자도서열람됨
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        ViewHistory viewHistory = new ViewHistory();
        repository().save(viewHistory);

        */

        /** Example 2:  finding and process
        

        repository().findById(월구독자도서열람됨.get???()).ifPresent(viewHistory->{
            
            viewHistory // do something
            repository().save(viewHistory);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void registerViewHistory(PointUsed pointUsed) {
        //implement business logic here:

        /** Example 1:  new item 
        ViewHistory viewHistory = new ViewHistory();
        repository().save(viewHistory);

        */

        /** Example 2:  finding and process
        

        repository().findById(pointUsed.get???()).ifPresent(viewHistory->{
            
            viewHistory // do something
            repository().save(viewHistory);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
