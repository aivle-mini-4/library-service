package aivle.domain;

import aivle.BooksmanagementApplication;
import aivle.domain.BestsellerRegistered;
import aivle.domain.BookDeleted;
import aivle.domain.BookRegistered;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Books_table")
@Data
//<<< DDD / Aggregate Root
public class Books {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String authorId;

    private String coverImageUrl;

    private String title;

    private String summary;

    private String category;

    private Integer price;

    private String content;

    private Integer views;

    @PreRemove
    public void onPreRemove() {
        BookDeleted bookDeleted = new BookDeleted(this);
        bookDeleted.publishAfterCommit();
    }

    public static BooksRepository repository() {
        BooksRepository booksRepository = BooksmanagementApplication.applicationContext.getBean(
            BooksRepository.class
        );
        return booksRepository;
    }

    //<<< Clean Arch / Port Method
    public static void assignBestsellerBadge(
        ViewHistoryRegistered viewHistoryRegistered
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Books books = new Books();
        repository().save(books);

        */

        /** Example 2:  finding and process
        

        repository().findById(viewHistoryRegistered.get???()).ifPresent(books->{
            
            books // do something
            repository().save(books);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void registerBook(ReadyToPublish readyToPublish) {
        //implement business logic here:

        /** Example 1:  new item 
        Books books = new Books();
        repository().save(books);

        BookRegistered bookRegistered = new BookRegistered(books);
        bookRegistered.publishAfterCommit();
        */

        /** Example 2:  finding and process
        
        // if readyToPublish.llmOpenAiId exists, use it
        
        // ObjectMapper mapper = new ObjectMapper();
        // Map<, Object> aiServiceMap = mapper.convertValue(readyToPublish.getLlmOpenAiId(), Map.class);

        repository().findById(readyToPublish.get???()).ifPresent(books->{
            
            books // do something
            repository().save(books);

            BookRegistered bookRegistered = new BookRegistered(books);
            bookRegistered.publishAfterCommit();

         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
