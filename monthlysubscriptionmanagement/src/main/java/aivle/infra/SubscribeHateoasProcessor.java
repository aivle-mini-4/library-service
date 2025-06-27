package aivle.infra;

import aivle.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class SubscribeHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Subscribe>> {

    @Override
    public EntityModel<Subscribe> process(EntityModel<Subscribe> model) {
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/subscriberequest"
                )
                .withRel("subscriberequest")
        );
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/unsubscriberequest"
                )
                .withRel("unsubscriberequest")
        );

        return model;
    }
}
