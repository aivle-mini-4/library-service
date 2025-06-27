package aivle.infra;

import aivle.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class AuthorapprovalHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Authorapproval>> {

    @Override
    public EntityModel<Authorapproval> process(
        EntityModel<Authorapproval> model
    ) {
        return model;
    }
}
