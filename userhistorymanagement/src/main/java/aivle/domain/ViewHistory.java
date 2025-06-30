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

    // null 허용X
    @Column(nullable = false)
    private Integer bookId;

    // null 허용X
    @Column(nullable = false)
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
        try {
            this.bookId = registerViewHistoryCommand.getBookId();
            this.userId = registerViewHistoryCommand.getUserId();

            repository().save(this);

            ViewHistoryRegistered viewHistoryRegistered = new ViewHistoryRegistered(
                this
            );

            viewHistoryRegistered.publishAfterCommit();
        } catch (Exception e) {
            throw new RuntimeException("ViewHistory command 실패", e);
        }
    }

    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public static void registerViewHistory(
        MonthlyBookSubscribed monthlyBookSubscribed
    ) {
        // implement business logic here:
        try{
            // new item 
            ViewHistory viewHistory = new ViewHistory();
            
            // set
            viewHistory.setBookId(monthlyBookSubscribed.getBookId());

            repository().save(viewHistory);

            ViewHistoryRegistered event = new ViewHistoryRegistered(viewHistory);
            event.publishAfterCommit();
        } catch (Exception e) {
            throw new RuntimeException("MonthlyBookSubscribed 이벤트 적용 실패", e);
        }
    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void registerViewHistory(PointUsed pointUsed) {
        // implement business logic here:
        try {
            // new item 
            ViewHistory viewHistory = new ViewHistory();

            viewHistory.setUserId(pointUsed.getUserId());

            repository().save(viewHistory);

            ViewHistoryRegistered event = new ViewHistoryRegistered(viewHistory);
            event.publishAfterCommit();
        } catch (Exception e) {
            throw new RuntimeException("PointUsed 이벤트 적용 실패", e);
        }
    }
    //>>> Clean Arch / Port Method
}
//>>> DDD / Aggregate Root
