package aivle.domain.Author;

import aivle.UserinfomanagementApplication;
import aivle.domain.AuthorApproved;
import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

@Entity
@Table(name = "AuthorProfile_table")
@Data
public class AuthorProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // 자동 생성으로 복원
    private Long id;
    private String name;
    private String email;
    private String roles;
    private String basicInformation;
    private String selfIntroduction;
    private String portfolio;
    private LocalDateTime updatedAt;
   
    // 해당 엔티티가 DB에 처음 저장된 후(INSERT 후) 자동으로 실행될 메서드를 지정
    @PostPersist
    public void onPostPersist() {
        AuthorProfileCreated event = new AuthorProfileCreated(this);
        event.publishAfterCommit();  // 이벤트를 트랜잭션 커밋 이후에 Kafka로 발행
    }

    public static AuthorProfileRepository repository() {
        return UserinfomanagementApplication.applicationContext.getBean(
            AuthorProfileRepository.class
        );
    }

    public static void createAuthorProfile(AuthorApproved authorApproved) {
        AuthorProfile author = new AuthorProfile();
        author.setId(authorApproved.getAuthorId());

        // 필드가 이벤트에 없더라도 시스템이 자동 생성할 수 있게 기본값으로 설정
        author.setName(null);
        author.setEmail(null);
        author.setRoles(null);
        author.setBasicInformation(null);
        author.setSelfIntroduction(null);
        author.setPortfolio(null);
        author.setUpdatedAt(LocalDateTime.now());

        repository().save(author);
        AuthorProfileCreated event = new AuthorProfileCreated(author);
        event.publishAfterCommit();
    }
   
    public void updateAuthorProfile(String name, String email, String basicInformation, String selfIntroduction, String portfolio) {
        this.setName(name);
        this.setEmail(email);
        this.setBasicInformation(basicInformation);
        this.setSelfIntroduction(selfIntroduction);
        this.setPortfolio(portfolio);
        this.setUpdatedAt(LocalDateTime.now());

        repository().save(this);
        AuthorProfileUpdated event = new AuthorProfileUpdated(this);
        event.publishAfterCommit();
    }
}
