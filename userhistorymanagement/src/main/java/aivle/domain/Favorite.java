package aivle.domain;

import aivle.UserhistorymanagementApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;
import java.time.LocalDate;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;


@Entity
@Table(name="Favorite_table")
@Data

//<<< DDD / Aggregate Root
public class Favorite  {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;    
    
    private Long bookId;    
    
    private Long userId;

    
    public static FavoriteRepository repository(){
        FavoriteRepository favoriteRepository = UserhistorymanagementApplication.applicationContext.getBean(FavoriteRepository.class);
        return favoriteRepository;
    }


    //<<< Clean Arch / Port Method
    public void registerFavorite(RegisterFavoriteCommand registerFavoriteCommand){
        
        this.userId = registerFavoriteCommand.getUserId();
        
        FavoriteRegistrerd favoriteRegistrerd = new FavoriteRegistrerd(this);
        favoriteRegistrerd.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public void deleteFavorite(DeleteFavoriteCommand deleteFavoriteCommand){
        
        this.userId = deleteFavoriteCommand.getUserId();

        FavoriteDeleted favoriteDeleted = new FavoriteDeleted(this);
        favoriteDeleted.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method
}
//>>> DDD / Aggregate Root