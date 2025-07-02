package aivle.domain;

import aivle.domain.*;
import aivle.infra.AbstractEvent;
import java.util.*;

public class SignedUp extends AbstractEvent {

    private Long id;
    private Long userId;
    private String name;
    private String email;
    private String roles;
    private String basicInformation;
    private String password;
    private Date createdAt;
    private Date updatedAt;

    // Getters
    public Long getId() { return id; }
    public Long getUserId() { return userId; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRoles() { return roles; }
    public String getBasicInformation() { return basicInformation; }
    public String getPassword() { return password; }
    public Date getCreatedAt() { return createdAt; }
    public Date getUpdatedAt() { return updatedAt; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setUserId(Long userId) { this.userId = userId; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRoles(String roles) { this.roles = roles; }
    public void setBasicInformation(String basicInformation) { this.basicInformation = basicInformation; }
    public void setPassword(String password) { this.password = password; }
    public void setCreatedAt(Date createdAt) { this.createdAt = createdAt; }
    public void setUpdatedAt(Date updatedAt) { this.updatedAt = updatedAt; }

    @Override
    public String toString() {
        return "SignedUp{" +
                "id=" + id +
                ", userId=" + userId +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", basicInformation='" + basicInformation + '\'' +
                ", password='" + password + '\'' +
                ", createdAt=" + createdAt +
                ", updatedAt=" + updatedAt +
                '}';
    }
}
