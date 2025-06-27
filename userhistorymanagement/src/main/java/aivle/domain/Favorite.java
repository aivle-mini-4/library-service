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
    
    
private Integer bookId;    
    
    
private Integer userId;


    public static FavoriteRepository repository(){
        FavoriteRepository favoriteRepository = UserhistorymanagementApplication.applicationContext.getBean(FavoriteRepository.class);
        return favoriteRepository;
    }



//<<< Clean Arch / Port Method
    public void registerFavorite(RegisterFavoriteCommand registerFavoriteCommand){
        
        //implement business logic here:
        

        aivle.external.FavoriteQuery favoriteQuery = new aivle.external.FavoriteQuery();
        // favoriteQuery.set??()        
          = FavoriteApplication.applicationContext
            .getBean(aivle.external.Service.class)
            .favorite(favoriteQuery);

        FavoriteRegistrerd favoriteRegistrerd = new FavoriteRegistrerd(this);
        favoriteRegistrerd.publishAfterCommit();
    }
//>>> Clean Arch / Port Method
//<<< Clean Arch / Port Method
    public void deleteFavorite(DeleteFavoriteCommand deleteFavoriteCommand){
        
        //implement business logic here:
        


        FavoriteDeleted favoriteDeleted = new FavoriteDeleted(this);
        favoriteDeleted.publishAfterCommit();
    }
//>>> Clean Arch / Port Method



}
//>>> DDD / Aggregate Root
