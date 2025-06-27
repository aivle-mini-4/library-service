package aivle.infra;

import aivle.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class UserAccountHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<UserAccount>> {

    @Override
    public EntityModel<UserAccount> process(EntityModel<UserAccount> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/signup")
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
