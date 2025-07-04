package aivle.domain.Member;

import java.time.LocalDateTime;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "MemberView_table")
@Data
public class MemberView {

    @Id
    private Long id;
    private String name;
    private String email;
    private String basicInfo;
    private LocalDateTime lastUpdatedAt;
    private LocalDateTime joinedAt;
}
