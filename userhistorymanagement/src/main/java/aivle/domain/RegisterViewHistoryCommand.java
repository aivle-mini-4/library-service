package aivle.domain;

import java.time.LocalDate;
import java.util.*;
import lombok.Data;

@Data
public class RegisterViewHistoryCommand {

    private Long id;
    private Long bookId;
    private Long userId;
}
