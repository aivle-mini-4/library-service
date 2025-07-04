package aivle.infra.Author;

import aivle.domain.Author.AuthorProfileCreated;
import aivle.domain.Author.AuthorProfileUpdated;
import aivle.domain.Author.AuthorView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class AuthorViewHandler {

    @Autowired
    private AuthorViewRepository authorViewRepository;

    @EventListener
    public void whenAuthorProfileCreated_then_CreateAuthorView(AuthorProfileCreated authorProfileCreated) {
        AuthorView authorView = new AuthorView();
        authorView.setId(authorProfileCreated.getId());
        authorView.setName(authorProfileCreated.getName());
        authorView.setEmail(authorProfileCreated.getEmail());
        authorView.setBasicInformation(authorProfileCreated.getBasicInformation());
        authorView.setSelfIntroduction(authorProfileCreated.getSelfIntroduction());
        authorView.setPortfolio(authorProfileCreated.getPortfolio());
        
        authorViewRepository.save(authorView);
    }

    @EventListener
    public void whenAuthorProfileUpdated_then_UpdateAuthorView(AuthorProfileUpdated authorProfileUpdated) {
        AuthorView authorView = authorViewRepository.findById(authorProfileUpdated.getId())
            .orElse(new AuthorView());
        
        authorView.setId(authorProfileUpdated.getId());
        authorView.setName(authorProfileUpdated.getName());
        authorView.setEmail(authorProfileUpdated.getEmail());
        authorView.setBasicInformation(authorProfileUpdated.getBasicInformation());
        authorView.setSelfIntroduction(authorProfileUpdated.getSelfIntroduction());
        authorView.setPortfolio(authorProfileUpdated.getPortfolio());
        
        authorViewRepository.save(authorView);
    }
}
