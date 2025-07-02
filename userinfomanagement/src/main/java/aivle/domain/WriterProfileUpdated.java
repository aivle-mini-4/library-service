package aivle.domain;

import aivle.infra.AbstractEvent;
import java.util.*;

//<<< DDD / Domain Event
public class WriterProfileUpdated extends AbstractEvent {

    private Long id;
    private String name;
    private String email;
    private String roles;
    private String basicInformation;
    private String selfIntroduction;
    private String portfolio;
    private Date updatedAt;
    private Long userId;

    public WriterProfileUpdated(WriterProfile aggregate) {
        super(aggregate);
        this.userId = aggregate.getId();
        this.name = aggregate.getName();
        this.email = aggregate.getEmail();
        this.roles = aggregate.getRoles();
        this.basicInformation = aggregate.getBasicInformation();
        this.selfIntroduction = aggregate.getSelfIntroduction();
        this.portfolio = aggregate.getPortfolio();
        this.updatedAt = aggregate.getUpdatedAt();
    }

    public WriterProfileUpdated() {
        super();
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRoles() { return roles; }
    public String getBasicInformation() { return basicInformation; }
    public String getSelfIntroduction() { return selfIntroduction; }
    public String getPortfolio() { return portfolio; }
    public Date getUpdatedAt() { return updatedAt; }
    public Long getUserId() { return userId; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRoles(String roles) { this.roles = roles; }
    public void setBasicInformation(String basicInformation) { this.basicInformation = basicInformation; }
    public void setSelfIntroduction(String selfIntroduction) { this.selfIntroduction = selfIntroduction; }
    public void setPortfolio(String portfolio) { this.portfolio = portfolio; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public void setUserId(Long userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "WriterProfileUpdated{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", basicInformation='" + basicInformation + '\'' +
                ", selfIntroduction='" + selfIntroduction + '\'' +
                ", portfolio='" + portfolio + '\'' +
                ", updatedAt=" + updatedAt +
                ", userId=" + userId +
                '}';
    }
}
//>>> DDD / Domain Event
