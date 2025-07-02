package aivle.domain;

import aivle.infra.AbstractEvent;
import java.util.Date;

public class CreateWriterProfile extends AbstractEvent {

    private Long id;
    private Long authorId;
    private Date resultAt;
    private String state;
    private String name;
    private String email;
    private String roles;
    private String basicInformation;
    private String selfIntroduction;
    private String portfolio;

    // Getters
    public Long getId() { return id; }
    public Long getAuthorId() { return authorId; }
    public Date getResultAt() { return resultAt; }
    public String getState() { return state; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRoles() { return roles; }
    public String getBasicInformation() { return basicInformation; }
    public String getSelfIntroduction() { return selfIntroduction; }
    public String getPortfolio() { return portfolio; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setAuthorId(Long authorId) { this.authorId = authorId; }
    public void setResultAt(Date resultAt) { this.resultAt = resultAt; }
    public void setState(String state) { this.state = state; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRoles(String roles) { this.roles = roles; }
    public void setBasicInformation(String basicInformation) { this.basicInformation = basicInformation; }
    public void setSelfIntroduction(String selfIntroduction) { this.selfIntroduction = selfIntroduction; }
    public void setPortfolio(String portfolio) { this.portfolio = portfolio; }

    @Override
    public String toString() {
        return "CreateWriterProfile{" +
                "id=" + id +
                ", authorId=" + authorId +
                ", resultAt=" + resultAt +
                ", state='" + state + '\'' +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", basicInformation='" + basicInformation + '\'' +
                ", selfIntroduction='" + selfIntroduction + '\'' +
                ", portfolio='" + portfolio + '\'' +
                '}';
    }
} 