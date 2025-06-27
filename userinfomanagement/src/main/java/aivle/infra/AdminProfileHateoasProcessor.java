package aivle.infra;

import aivle.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class AdminProfileHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<AdminProfile>> {

    @Override
    public EntityModel<AdminProfile> process(EntityModel<AdminProfile> model) {
        return model;
    }
}
