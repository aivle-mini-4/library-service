package aivle.infra;

import aivle.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class ViewHistoryHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<ViewHistory>> {

    @Override
    public EntityModel<ViewHistory> process(EntityModel<ViewHistory> model) {
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/registerviewhistory"
                )
                .withRel("registerviewhistory")
        );

        return model;
    }
}
