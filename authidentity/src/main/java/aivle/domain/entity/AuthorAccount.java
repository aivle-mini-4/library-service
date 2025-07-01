package aivle.domain.entity;

import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import aivle.AuthidentityApplication;
import aivle.domain.command.AuthorSignupCommand;
import aivle.domain.command.LoginCommand;
import aivle.domain.command.LogoutCommand;
import aivle.domain.event.AuthorSignup;
import aivle.domain.event.Logged;
import aivle.domain.event.Loggedout;
import aivle.domain.repository.AuthorAccountRepository;
import aivle.domain.valueobject.EmailValidator;
import aivle.domain.valueobject.UserRole;
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
    private LocalDateTime createdAt;    
    private LocalDateTime updatedAt;
    private String selfIntroduction;
    private String portfolio;

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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getSelfIntroduction() {
        return selfIntroduction;
    }

    public void setSelfIntroduction(String selfIntroduction) {
        this.selfIntroduction = selfIntroduction;
    }

    public String getPortfolio() {
        return portfolio;
    }

    public void setPortfolio(String portfolio) {
        this.portfolio = portfolio;
    }

    public static AuthorAccountRepository repository(){
        AuthorAccountRepository authorAccountRepository = AuthidentityApplication.applicationContext.getBean(AuthorAccountRepository.class);
        return authorAccountRepository;
    }

    public void signup(AuthorSignupCommand authorSignupCommand){
        // 이메일 유효성 검사
        EmailValidator.validateEmail(authorSignupCommand.getEmail());
        
        // 작가 회원가입 + 작가권한요청 이벤트
        // 이메일 중복 체크
        if (repository().findByEmail(authorSignupCommand.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + authorSignupCommand.getEmail());
        }

        // 현재 객체에 정보 설정
        this.setEmail(authorSignupCommand.getEmail());
        this.setSelfIntroduction(authorSignupCommand.getSelfIntroduction());
        this.setPortfolio(authorSignupCommand.getPortfolio());
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());

        // 비밀번호 암호화
        org.springframework.security.crypto.password.PasswordEncoder passwordEncoder = 
            AuthidentityApplication.applicationContext.getBean(org.springframework.security.crypto.password.PasswordEncoder.class);
        this.setPassword(passwordEncoder.encode(authorSignupCommand.getPassword()));

        // 저장하고 flush하여 id 즉시 생성
        AuthorAccount savedAccount = repository().saveAndFlush(this);

        // 저장된 엔티티로 이벤트 publish (id가 설정된 객체)
        AuthorSignup authorSignup = new AuthorSignup(savedAccount);
        authorSignup.publishAfterCommit();
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
