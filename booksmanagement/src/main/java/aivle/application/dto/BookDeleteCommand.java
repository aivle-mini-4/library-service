package aivle.application.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class BookDeleteCommand {
    private Long bookId;
}
