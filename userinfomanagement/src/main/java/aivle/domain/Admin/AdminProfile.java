package aivle.domain.Admin;

import aivle.UserinfomanagementApplication;
import aivle.domain.SignedUp;
import javax.persistence.*;
import lombok.*;

@Entity
@Table(name = "AdminProfile_table")
@Data
public class AdminProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String email;
    private String roles;

    // 해당 엔티티가 DB에 처음 저장된 후(INSERT 후) 자동으로 실행될 메서드를 지정
    @PostPersist
    public void onPostPersist() {
        // 이벤트 객체 생성 후, 커밋 이후 발행하도록 지정
        AdminProfileCreated event = new AdminProfileCreated(this);
        event.publishAfterCommit();
    }

    public static AdminProfileRepository repository() {
        return UserinfomanagementApplication.applicationContext.getBean(
                AdminProfileRepository.class);
    }
    
    // 회원 가입(SignUp) 이벤트로부터 AdminProfile 생성
    public static void createAdminProfile(SignedUp signedUp) {
        AdminProfile admin = new AdminProfile();
        admin.setId(signedUp.getId());
        // name 필드가 SignedUp에 없으므로 null
        admin.setName(null);
        admin.setEmail(signedUp.getEmail());
        admin.setRoles(signedUp.getRoles());
        
        repository().save(admin);
        AdminProfileCreated event = new AdminProfileCreated(admin);
        event.publishAfterCommit();
    }
}
