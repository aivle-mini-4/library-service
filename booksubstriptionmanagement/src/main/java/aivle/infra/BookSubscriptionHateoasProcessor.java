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
        return model;
    }
}
