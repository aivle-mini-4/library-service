package aivle.infra;

import aivle.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class MemberProfileHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<MemberProfile>> {

    @Override
    public EntityModel<MemberProfile> process(
        EntityModel<MemberProfile> model
    ) {
        return model;
    }
}
