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
import aivle.domain.command.LoginCommand;
import aivle.domain.command.LogoutCommand;
import aivle.domain.command.SignupCommand;
import aivle.domain.event.Logged;
import aivle.domain.event.SignedUp;
import aivle.domain.repository.AdminAccountRepository;
import aivle.domain.valueobject.EmailValidator;
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
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

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

    public static AdminAccountRepository repository() {
        AdminAccountRepository adminAccountRepository = AuthidentityApplication.applicationContext.getBean(
            AdminAccountRepository.class
        );
        return adminAccountRepository;
    }

    public void signup(SignupCommand signupCommand) {
        // 이메일 유효성 검사
        EmailValidator.validateEmail(signupCommand.getEmail());
        
        // 기존 이메일 중복 검사
        if (repository().findByEmail(signupCommand.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 존재하는 이메일입니다: " + signupCommand.getEmail());
        }
        
        this.setEmail(signupCommand.getEmail());
        
        // 비밀번호 암호화
        org.springframework.security.crypto.password.PasswordEncoder passwordEncoder = 
            AuthidentityApplication.applicationContext.getBean(org.springframework.security.crypto.password.PasswordEncoder.class);
        this.setPassword(passwordEncoder.encode(signupCommand.getPassword()));
        
        this.setCreatedAt(LocalDateTime.now());
        this.setUpdatedAt(LocalDateTime.now());
        
        // 저장하고 flush하여 id 즉시 생성
        AdminAccount savedAccount = repository().saveAndFlush(this);

        // 저장된 엔티티로 이벤트 publish (id가 설정된 객체)
        SignedUp signedUp = new SignedUp(savedAccount);
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
