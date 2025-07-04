package aivle.domain.Admin;

import javax.persistence.*;
import lombok.Data;

//<<< EDA / CQRS
@Entity
@Table(name = "AdminView_table")
@Data
public class AdminView {

    @Id
    private Long id;
    private String name;
}
