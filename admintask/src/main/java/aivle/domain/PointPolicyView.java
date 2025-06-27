package aivle.domain;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "PointPolicyView_table")
@Data
public class PointPolicyView {

    @Id
    //@GeneratedValue(strategy=GenerationType.AUTO)
    private Long id;

    private String policyId;
    private String name;
    private String description;
    private String pointType;
    private Integer amount;
    private Boolean isActive;
    private Date createdAt;
    private Date updatedAt;
}
