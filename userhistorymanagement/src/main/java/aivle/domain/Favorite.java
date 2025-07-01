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

    private Long userId;

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
}
//>>> DDD / Aggregate Root
