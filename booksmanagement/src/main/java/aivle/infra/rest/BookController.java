package aivle.infra.rest;

import javax.transaction.Transactional;

import aivle.domain.repository.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/books")
@Transactional
public class BookController {

    @Autowired
    BookRepository bookRepository;
}
//>>> Clean Arch / Inbound Adaptor
