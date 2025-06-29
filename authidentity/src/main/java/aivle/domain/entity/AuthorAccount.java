package aivle.domain.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import aivle.AuthidentityApplication;
import aivle.domain.command.LoginCommand;
import aivle.domain.command.LogoutCommand;
import aivle.domain.command.RequestAuthorRegistrationCommand;
import aivle.domain.enums.UserRole;
import aivle.domain.event.AuthorRegistrationRequested;
import aivle.domain.event.Logged;
import aivle.domain.event.Loggedout;
import aivle.domain.repository.AuthorAccountRepository;
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
    private UserRole roles = UserRole.AUTHOR;    
    private Date createdAt;    
    private Date updatedAt;


    public static AuthorAccountRepository repository(){
        AuthorAccountRepository authorAccountRepository = AuthidentityApplication.applicationContext.getBean(AuthorAccountRepository.class);
        return authorAccountRepository;
    }



    public void requestAuthorRegistration(RequestAuthorRegistrationCommand requestAuthorRegistrationCommand){

        AuthorAccount authorAccount = new AuthorAccount();

        authorAccount.setEmail(requestAuthorRegistrationCommand.getEmail());
        authorAccount.setPassword(requestAuthorRegistrationCommand.getPassword());
        authorAccount.setCreatedAt(new Date());
        authorAccount.setUpdatedAt(new Date());

        repository().save(authorAccount);
        


        AuthorRegistrationRequested authorRegistrationRequested = new AuthorRegistrationRequested(this);
        authorRegistrationRequested.publishAfterCommit();
    }


    public void logout(LogoutCommand logoutCommand){
        
        //implement business logic here:
        


        Loggedout loggedout = new Loggedout(this);
        loggedout.publishAfterCommit();
    }

    public void login(LoginCommand loginCommand){
        
        //implement business logic here:
        


        Logged logged = new Logged(this);
        logged.publishAfterCommit();
    }



}
//>>> DDD / Aggregate Root
