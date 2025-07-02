package aivle.infra;

import aivle.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class BookSubscriptionHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<BookSubscription>> {

    @Override
    public EntityModel<BookSubscription> process(
        EntityModel<BookSubscription> model
    ) {
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/subscribebook")
                .withRel("subscribebook")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/viewbook")
                .withRel("viewbook")
        );

        return model;
    }
}
