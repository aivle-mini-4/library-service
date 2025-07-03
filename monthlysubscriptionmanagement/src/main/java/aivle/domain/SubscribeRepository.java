package aivle.domain;

import aivle.domain.*;
import java.util.Date;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

//<<< PoEAA / Repository
public interface SubscribeRepository
    extends PagingAndSortingRepository<Subscribe, Long> {

    Subscribe findByUserId(Long userId);}