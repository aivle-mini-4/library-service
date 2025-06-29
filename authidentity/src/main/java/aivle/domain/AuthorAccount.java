package aivle.domain;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import aivle.AuthidentityApplication;
import lombok.Data;

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
    @Enumerated(EnumType.STRING)
    private UserRole roles;    
    private Date createdAt;    
    private Date updatedAt;


    public static AuthorAccountRepository repository(){
        AuthorAccountRepository authorAccountRepository = AuthidentityApplication.applicationContext.getBean(AuthorAccountRepository.class);
        return authorAccountRepository;
    }



    public void requestAuthorRegistration(RequestAuthorRegistrationCommand requestAuthorRegistrationCommand){

        AuthorAccount authorAccount = new AuthorAccount();
        // private String password;
        // private String email;
        // private String selfIntroduction;
        // private String portfolio;
        authorAccount.setEmail(requestAuthorRegistrationCommand.getEmail());
        authorAccount.setPassword(requestAuthorRegistrationCommand.getPassword());
        authorAccount.setRoles(requestAuthorRegistrationCommand.getRoles());
        authorAccount.setCreatedAt(new Date());
        authorAccount.setUpdatedAt(new Date());
        

        AuthorRegistrationRequested authorRegistrationRequested = new AuthorRegistrationRequested(this);
        authorRegistrationRequested.publishAfterCommit();
    }


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
