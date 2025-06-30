package aivle.domain;

import aivle.UserhistorymanagementApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;
import java.time.LocalDate;
import java.util.Map;
import java.util.concurrent.ExecutionException;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;


@Entity
@Table(name = "Favorite_table")
@Data

//<<< DDD / Aggregate Root
public class Favorite  {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;    
       
    private Long bookId;      

    private String userId;

    @PostPersist
    public void onPostPersist() {
        FavoriteRegistrerd favoriteRegistrerd = new FavoriteRegistrerd(this);
        favoriteRegistrerd.publishAfterCommit();

        FavoriteDeleted favoriteDeleted = new FavoriteDeleted(this);
        favoriteDeleted.publishAfterCommit();
    }

    public static FavoriteRepository repository(){
        FavoriteRepository favoriteRepository = UserhistorymanagementApplication.applicationContext.getBean(FavoriteRepository.class);
        return favoriteRepository;
    }


//<<< Clean Arch / Port Method
    // public void registerFavorite(RegisterFavoriteCommand registerFavoriteCommand){
        
    //     //implement business logic here:
    //     try {
    //         this.bookId = registerFavoriteCommand.getBookId();
    //         this.userId = registerFavoriteCommand.getUserId();

    //         aivle.external.FavoriteQuery favoriteQuery = new aivle.external.FavoriteQuery();
    //         favoriteQuery.setBookId(this.bookId);
    //         favoriteQuery.setUserId(this.userId);
            
    //         aivle.external.Service externalService =
    //             UserhistorymanagementApplication.applicationContext
    //                 .getBean(aivle.external.Service.class);

            
    //         externalService.favorite(favoriteQuery);

    //         repository().save(this);

    //         FavoriteRegistrerd favoriteRegistrerd = new FavoriteRegistrerd(this);
    //         favoriteRegistrerd.publishAfterCommit();
    //     } catch (Exception e) {
    //         throw new RuntimeException("RegisterFavorite command 실패", e);
    //     }
    // }
//>>> Clean Arch / Port Method
//<<< Clean Arch / Port Method
    // public void deleteFavorite(DeleteFavoriteCommand deleteFavoriteCommand){
        
    //     //implement business logic here:
    //     try {
    //         repository().deleteById(deleteFavoriteCommand.getUserId());


    //         FavoriteDeleted favoriteDeleted = new FavoriteDeleted(this);
    //         favoriteDeleted.publishAfterCommit();
    //     } catch (Exception e) {
    //         throw new RuntimeException("DeleteFavorite command 실패", e);
    //     }
    // }
//>>> Clean Arch / Port Method
}
//>>> DDD / Aggregate Root
