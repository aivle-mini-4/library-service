package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.time.LocalDate;
import java.util.*;

//<<< DDD / Domain Event
public class MemberProfileUpdated extends AbstractEvent {

    private Long id;
    private String name;
    private String email;
    private String roles;
    private String basicInformation;
    private Date updatedAt;
    private Long userId;

    public MemberProfileUpdated(MemberProfile aggregate) {
        super(aggregate);
        this.userId = aggregate.getId();
        this.name = aggregate.getName();
        this.email = aggregate.getEmail();
        this.roles = aggregate.getRoles();
        this.basicInformation = aggregate.getBasicInformation();
        this.updatedAt = aggregate.getUpdatedAt();
    }

    public MemberProfileUpdated() {
        super();
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRoles() { return roles; }
    public String getBasicInformation() { return basicInformation; }
    public Date getUpdatedAt() { return updatedAt; }
    public Long getUserId() { return userId; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRoles(String roles) { this.roles = roles; }
    public void setBasicInformation(String basicInformation) { this.basicInformation = basicInformation; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }
    public void setUserId(Long userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "MemberProfileUpdated{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", basicInformation='" + basicInformation + '\'' +
                ", updatedAt=" + updatedAt +
                ", userId=" + userId +
                '}';
    }
}
//>>> DDD / Domain Event
