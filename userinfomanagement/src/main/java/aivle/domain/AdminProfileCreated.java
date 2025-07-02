package aivle.domain;

import aivle.infra.AbstractEvent;

//<<< DDD / Domain Event
public class AdminProfileCreated extends AbstractEvent {

    private Long id;
    private String name;
    private String email;
    private String roles;
    private Long userId;

    public AdminProfileCreated(AdminProfile aggregate) {
        super(aggregate);
        this.userId = aggregate.getId();
        this.name = aggregate.getName();
        this.email = aggregate.getEmail();
        this.roles = aggregate.getRoles();
    }

    public AdminProfileCreated() {
        super();
    }

    // Getters
    public Long getId() { return id; }
    public String getName() { return name; }
    public String getEmail() { return email; }
    public String getRoles() { return roles; }
    public Long getUserId() { return userId; }

    // Setters
    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setEmail(String email) { this.email = email; }
    public void setRoles(String roles) { this.roles = roles; }
    public void setUserId(Long userId) { this.userId = userId; }

    @Override
    public String toString() {
        return "AdminProfileCreated{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", email='" + email + '\'' +
                ", roles='" + roles + '\'' +
                ", userId=" + userId +
                '}';
    }
}
//>>> DDD / Domain Event
