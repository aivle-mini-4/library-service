package aivle.infra;

import aivle.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class AdminAccountHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<AdminAccount>> {

    @Override
    public EntityModel<AdminAccount> process(EntityModel<AdminAccount> model) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/signup")
                .withRel("signup")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/login")
                .withRel("login")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/logout")
                .withRel("logout")
        );

        return model;
    }
}
