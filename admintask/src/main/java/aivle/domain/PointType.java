package aivle.domain;

import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;
import lombok.Data;
import org.springframework.beans.BeanUtils;

public enum PointType {
    ACCUMULATION,
    USAGE,
    EXPIRATION,
}
