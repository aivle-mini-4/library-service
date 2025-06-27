package aivle.infra;

import aivle.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class AiServiceHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<AiService>> {

    @Override
    public EntityModel<AiService> process(EntityModel<AiService> model) {
        return model;
    }
}
