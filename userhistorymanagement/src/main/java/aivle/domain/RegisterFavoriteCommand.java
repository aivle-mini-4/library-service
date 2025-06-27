package aivle.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RegisterFavoriteCommand {

    private Long id;
    private Integer bookId;
    private Integer userId;
}
