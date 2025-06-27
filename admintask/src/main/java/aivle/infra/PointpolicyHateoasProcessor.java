package aivle.infra;

import aivle.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class PointpolicyHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Pointpolicy>> {

    @Override
    public EntityModel<Pointpolicy> process(EntityModel<Pointpolicy> model) {
        return model;
    }
}
