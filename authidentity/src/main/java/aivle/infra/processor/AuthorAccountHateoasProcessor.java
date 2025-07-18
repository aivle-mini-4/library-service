package aivle.infra.processor;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

import aivle.domain.entity.AuthorAccount;

@Component
public class AuthorAccountHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<AuthorAccount>> {

    @Override
    public EntityModel<AuthorAccount> process(
        EntityModel<AuthorAccount> model
    ) {
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/signup"
                )
                .withRel("signup")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/logout")
                .withRel("logout")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/login")
                .withRel("login")
        );

        return model;
    }
}
