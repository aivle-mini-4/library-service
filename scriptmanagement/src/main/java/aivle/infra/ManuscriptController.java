package aivle.infra;

import aivle.domain.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import java.util.List;
import java.util.ArrayList;



@RestController
@RequestMapping(value="/manuscripts")    
@Transactional
public class ManuscriptController {

    @Autowired
    ManuscriptRepository manuscriptRepository;

    @Autowired
    ManuscriptPageRepository manuscriptPageRepository;

    @PostMapping
    public Manuscript createManuscript(
        @RequestHeader(value = "X-User-Id",required = false) Long authorId,
        @RequestBody Manuscript manuscript
    ) {
        manuscript.setAuthorId(authorId);
        return manuscriptRepository.save(manuscript);
    }

    @PutMapping("/{id}")
    public Manuscript putMethodName(
        @RequestHeader(value = "X-User-Id",required = false) Long authorId,
        @PathVariable Long id,
        @RequestBody Manuscript manuscript
    ) {
        manuscript.setId(id);
        manuscript.setAuthorId(authorId);
        return manuscriptRepository.save(manuscript);
    }

    @DeleteMapping("/{id}")
    public void deleteManuscript(@PathVariable Long id) {
        manuscriptRepository.deleteById(id);
    }

    @PostMapping("/{id}")
    public void requestPublication(@PathVariable Long id) {
        Manuscript manuscript = manuscriptRepository.findById(id).orElseThrow(() -> new RuntimeException("Manuscript not found"));
        PublicationRequested publicationRequested = new PublicationRequested(manuscript);
        publicationRequested.publishAfterCommit();
    }

    @GetMapping
    public List<ManuscriptPage> getAllManuscripts(
            @RequestHeader(value = "X-User-Id", required = false) String userId,
            @RequestHeader(value = "X-User-Role", required = false) String userRole) {
        
        // ROLE_AUTHOR가 아닌 경우
        if (!"ROLE_AUTHOR".equals(userRole)) {
            throw new RuntimeException("Unauthorized");
        }
        
        // userId가 null이거나 빈 문자열인 경우
        if (userId == null || userId.trim().isEmpty()) {
            throw new RuntimeException("Unauthorized");
        }
        
        try {
            Long authorId = Long.parseLong(userId);
            Iterable<ManuscriptPage> pages = manuscriptPageRepository.findAll();
            List<ManuscriptPage> result = new ArrayList<>();
            
            // authorId로 필터링
            pages.forEach(page -> {
                if (authorId.equals(page.getAuthorId())) {
                    result.add(page);
                }
            });
            
            return result;
        } catch (NumberFormatException e) {

            throw new RuntimeException("Unauthorized");
        }
    }

    @GetMapping("/{id}")
    public ManuscriptPage getManuscript(@PathVariable Long id) {
        return manuscriptPageRepository.findById(id).orElseThrow(() -> new RuntimeException("ManuscriptPage not found"));
    }
}
