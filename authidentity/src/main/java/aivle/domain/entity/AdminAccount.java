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
import aivle.domain.command.SignupCommand;
import aivle.domain.event.Logged;
import aivle.domain.event.SignedUp;
import aivle.domain.repository.AdminAccountRepository;
import aivle.domain.valueobject.UserRole;
import lombok.Data;

@Entity
@Table(name = "AdminAccount_table")
@Data
//<<< DDD / Aggregate Root
public class AdminAccount {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String email;
    private String password;
    @Enumerated(EnumType.STRING)
    private UserRole roles = UserRole.ADMIN;
    private Date createdAt;
    private Date updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public UserRole getRoles() {
        return roles;
    }

    public void setRoles(UserRole roles) {
        this.roles = roles;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

    public static AdminAccountRepository repository() {
        AdminAccountRepository adminAccountRepository = AuthidentityApplication.applicationContext.getBean(
            AdminAccountRepository.class
        );
        return adminAccountRepository;
    }

    public void signup(SignupCommand signupCommand) {
        //implement business logic here:
        
        this.setEmail(signupCommand.getEmail());
        this.setPassword(signupCommand.getPassword());
        this.setCreatedAt(new Date());
        this.setUpdatedAt(new Date());
        
        repository().save(this);

        SignedUp signedUp = new SignedUp(this);
        signedUp.publishAfterCommit();
    }

    public void login(LoginCommand loginCommand) {
        //implement business logic here:

        Logged logged = new Logged(this);
        logged.publishAfterCommit();
    }


    public void logout(LogoutCommand logoutCommand) {
        //implement business logic here:

    }

}
//>>> DDD / Aggregate Root
