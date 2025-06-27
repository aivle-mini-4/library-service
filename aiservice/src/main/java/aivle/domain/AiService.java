package aivle.domain;

import aivle.AiserviceApplication;
import aivle.domain.ReadyToPublish;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "AiService_table")
@Data
//<<< DDD / Aggregate Root
public class AiService {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long manuscriptId;

    private String model;

    private String apiKey;

    @PostPersist
    public void onPostPersist() {
        ReadyToPublish readyToPublish = new ReadyToPublish(this);
        readyToPublish.publishAfterCommit();
    }

    public static AiServiceRepository repository() {
        AiServiceRepository aiServiceRepository = AiserviceApplication.applicationContext.getBean(
            AiServiceRepository.class
        );
        return aiServiceRepository;
    }

    //<<< Clean Arch / Port Method
    public static void autoPublishWithAi(
        PublicationRequested publicationRequested
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        AiService aiService = new AiService();
        repository().save(aiService);

        */

        /** Example 2:  finding and process
        

        repository().findById(publicationRequested.get???()).ifPresent(aiService->{
            
            aiService // do something
            repository().save(aiService);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
