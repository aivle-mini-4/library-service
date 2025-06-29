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



@RestController
@RequestMapping(value="/manuscripts")    
@Transactional
public class ManuscriptController {

    @Autowired
    ManuscriptRepository manuscriptRepository;

    @PostMapping
    public Manuscript createManuscript(@RequestBody Manuscript manuscript) {
        return manuscriptRepository.save(manuscript);
    }

    @PutMapping("/{id}")
    public Manuscript putMethodName(@PathVariable Long id, @RequestBody Manuscript manuscript) {
        manuscript.setId(id);
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
}
