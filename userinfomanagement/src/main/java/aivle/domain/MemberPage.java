package aivle.domain;

import java.util.Date;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "MemberPage_table")
@Data
public class MemberPage {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;
    private Long userId;
    private String name;
    private String email;
    private String basicInfo;
    private Date lastUpdatedAt;
    private Date joinedAt;
}
