package aivle.domain;

import aivle.UserinfomanagementApplication;
import aivle.domain.WriterProfileCreated;
import aivle.domain.WriterProfileUpdated;
import java.util.Date;
import javax.persistence.*;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Entity
@Table(name = "WriterProfile_table")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
//<<< DDD / Aggregate Root
public class WriterProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)  // 자동 생성으로 복원
    @JsonProperty("id")
    private Long id;
    private Long authorId;  // 별도 필드로 추가
    private String name;
    private String email;
    private String roles;
    private String basicInformation;
    private String selfIntroduction;
    private String portfolio;
    private Date updatedAt;

    // Getters
    public Long getId() { return id; }
    public Long getAuthorId() { return authorId; }  // ← 추가
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRoles() { return roles; }
    public String getBasicInformation() { return basicInformation; }
    public String getSelfIntroduction() { return selfIntroduction; }
    public String getPortfolio() { return portfolio; }
    public Date getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }  // ← 추가
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRoles(String roles) { this.roles = roles; }
    public void setBasicInformation(String basicInformation) { this.basicInformation = basicInformation; }
    public void setSelfIntroduction(String selfIntroduction) { this.selfIntroduction = selfIntroduction; }
    public void setPortfolio(String portfolio) { this.portfolio = portfolio; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    @PostPersist
    public void onPostPersist() {
        WriterProfileUpdated writerProfileUpdated = new WriterProfileUpdated(
            this
        );
        writerProfileUpdated.publishAfterCommit();

        WriterProfileCreated writerProfileCreated = new WriterProfileCreated(
            this
        );
        writerProfileCreated.publishAfterCommit();
    }

    public static WriterProfileRepository repository() {
        WriterProfileRepository writerProfileRepository = UserinfomanagementApplication.applicationContext.getBean(
            WriterProfileRepository.class
        );
        return writerProfileRepository;
    }

    //<<< Clean Arch / Port Method
    public static void createWriterProfile(CreateWriterProfile createWriterProfile) {
        WriterProfile writerProfile = new WriterProfile();
        // authorId 설정 (id는 자동 생성)
        writerProfile.setAuthorId(createWriterProfile.getAuthorId());
        writerProfile.setName(createWriterProfile.getName());
        writerProfile.setEmail(createWriterProfile.getEmail());
        writerProfile.setRoles(createWriterProfile.getRoles());
        writerProfile.setBasicInformation(createWriterProfile.getBasicInformation());
        writerProfile.setSelfIntroduction(createWriterProfile.getSelfIntroduction());
        writerProfile.setPortfolio(createWriterProfile.getPortfolio());
        writerProfile.setUpdatedAt(new Date());
        repository().save(writerProfile);
        WriterProfileCreated writerProfileCreated = new WriterProfileCreated(writerProfile);
        writerProfileCreated.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

    //<<< Clean Arch / Port Method
    public void updateWriterProfile(String name, String email, String roles, String basicInformation, String selfIntroduction, String portfolio) {
        this.setName(name);
        this.setEmail(email);
        this.setRoles(roles);
        this.setBasicInformation(basicInformation);
        this.setSelfIntroduction(selfIntroduction);
        this.setPortfolio(portfolio);
        this.setUpdatedAt(new Date());
        repository().save(this);
        WriterProfileUpdated writerProfileUpdated = new WriterProfileUpdated(this);
        writerProfileUpdated.publishAfterCommit();
    }
    //>>> Clean Arch / Port Method

    @Override
    public String toString() {
        return "WriterProfile{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", basicInformation='" + basicInformation + '\'' +
                ", selfIntroduction='" + selfIntroduction + '\'' +
                ", portfolio='" + portfolio + '\'' +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
//>>> DDD / Aggregate Root
