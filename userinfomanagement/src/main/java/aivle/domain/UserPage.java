package aivle.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "UserPage_table")
@Data
public class UserPage {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long userId;

    private String name;
    private String email;
    private String basicInfo;
    private Date lastUpdatedAt;
    private Date joinedAt;
    private String profileImageUrl;
}
