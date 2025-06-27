package aivle.domain;

import aivle.AuthidentityApplication;
import javax.persistence.*;
import java.util.List;
import lombok.Data;
import java.util.Date;
import java.time.LocalDate;
import java.util.Map;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Collections;


@Entity
@Table(name="AuthorAccount_table")
@Data

//<<< DDD / Aggregate Root
public class AuthorAccount  {

    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    
    
    
private Long id;    
    
    
private String email;    
    
    
private String password;    
    
    
private String roles;    
    
    
private Date createdAt;    
    
    
private Date updatedAt;


    public static AuthorAccountRepository repository(){
        AuthorAccountRepository authorAccountRepository = AuthidentityApplication.applicationContext.getBean(AuthorAccountRepository.class);
        return authorAccountRepository;
    }



//<<< Clean Arch / Port Method
    public void requestAuthorRegistration(RequestAuthorRegistrationCommand requestAuthorRegistrationCommand){
        
        //implement business logic here:
        

        aivle.external.WriterProfileQuery writerProfileQuery = new aivle.external.WriterProfileQuery();
        // writerProfileQuery.set??()        
          = AuthorAccountApplication.applicationContext
            .getBean(aivle.external.Service.class)
            .writerProfile(writerProfileQuery);

        AuthorRegistrationRequested authorRegistrationRequested = new AuthorRegistrationRequested(this);
        authorRegistrationRequested.publishAfterCommit();
    }
//>>> Clean Arch / Port Method
//<<< Clean Arch / Port Method
    public void logout(LogoutCommand logoutCommand){
        
        //implement business logic here:
        


        Loggedout loggedout = new Loggedout(this);
        loggedout.publishAfterCommit();
    }
//>>> Clean Arch / Port Method
//<<< Clean Arch / Port Method
    public void login(LoginCommand loginCommand){
        
        //implement business logic here:
        


        Logged logged = new Logged(this);
        logged.publishAfterCommit();
    }
//>>> Clean Arch / Port Method



}
//>>> DDD / Aggregate Root
