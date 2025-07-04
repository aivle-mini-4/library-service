package aivle.domain.Admin;

import aivle.infra.AbstractEvent;
import lombok.Data;
import lombok.EqualsAndHashCode;

//<<< DDD / Domain Event
@Data
@EqualsAndHashCode(callSuper = true)
public class AdminProfileCreated extends AbstractEvent {

    private Long id;
    private String name;
    private String email;
    private String roles;

    // 엔티티 기반 생성자: 이벤트 발행 시 사용
    public AdminProfileCreated(AdminProfile aggregate) {
        super(aggregate);
        this.id = aggregate.getId();
        this.name = aggregate.getName();
        this.email = aggregate.getEmail();
        this.roles = aggregate.getRoles();
    }

    // 기본 생성자
    public AdminProfileCreated() {
        super();
    }

}
//>>> DDD / Domain Event
