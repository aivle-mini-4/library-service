package aivle.infra;

import aivle.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class WriterProfileHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<WriterProfile>> {

    @Override
    public EntityModel<WriterProfile> process(
        EntityModel<WriterProfile> model
    ) {
        return model;
    }
}
