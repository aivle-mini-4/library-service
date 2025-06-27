package aivle.domain;

import aivle.PointmanagementApplication;
import aivle.domain.PointExpired;
import aivle.domain.PointUsed;
import aivle.domain.PointsGranted;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.time.LocalDate;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "Point_table")
@Data
//<<< DDD / Aggregate Root
public class Point {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private Long userId;

    private Integer points;

    private Date history;

    @PostPersist
    public void onPostPersist() {
        PointUsed pointUsed = new PointUsed(this);
        pointUsed.publishAfterCommit();

        PointExpired pointExpired = new PointExpired(this);
        pointExpired.publishAfterCommit();

        PointsGranted pointsGranted = new PointsGranted(this);
        pointsGranted.publishAfterCommit();
    }

    public static PointRepository repository() {
        PointRepository pointRepository = PointmanagementApplication.applicationContext.getBean(
            PointRepository.class
        );
        return pointRepository;
    }

    //<<< Clean Arch / Port Method
    public static void grantPoints(SignedUp signedUp) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        PointsGranted pointsGranted = new PointsGranted(point);
        pointsGranted.publishAfterCommit();
        */

        /** Example 2:  finding and process
        

        repository().findById(signedUp.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);

            PointsGranted pointsGranted = new PointsGranted(point);
            pointsGranted.publishAfterCommit();

         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void pointDeduction(
        ViewHistoryRegistered viewHistoryRegistered
    ) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        */

        /** Example 2:  finding and process
        

        repository().findById(viewHistoryRegistered.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void pointDeduction(BookSubscribed bookSubscribed) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        */

        /** Example 2:  finding and process
        

        repository().findById(bookSubscribed.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void pointManagement(PointPolicyCreated pointPolicyCreated) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        */

        /** Example 2:  finding and process
        

        repository().findById(pointPolicyCreated.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void pointManagement(PointPolicyUpdated pointPolicyUpdated) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        */

        /** Example 2:  finding and process
        

        repository().findById(pointPolicyUpdated.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);


         });
        */

    }

    //>>> Clean Arch / Port Method
    //<<< Clean Arch / Port Method
    public static void pointManagement(PointPolicyDeleted pointPolicyDeleted) {
        //implement business logic here:

        /** Example 1:  new item 
        Point point = new Point();
        repository().save(point);

        */

        /** Example 2:  finding and process
        

        repository().findById(pointPolicyDeleted.get???()).ifPresent(point->{
            
            point // do something
            repository().save(point);


         });
        */

    }
    //>>> Clean Arch / Port Method

}
//>>> DDD / Aggregate Root
