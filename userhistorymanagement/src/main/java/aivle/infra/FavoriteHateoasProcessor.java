package aivle.infra;

import aivle.domain.*;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.server.RepresentationModelProcessor;
import org.springframework.stereotype.Component;

@Component
public class FavoriteHateoasProcessor
    implements RepresentationModelProcessor<EntityModel<Favorite>> {

    @Override
    public EntityModel<Favorite> process(EntityModel<Favorite> model) {
        model.add(
            Link
                .of(
                    model.getRequiredLink("self").getHref() +
                    "/registerfavorite"
                )
                .withRel("registerfavorite")
        );
        model.add(
            Link
                .of(model.getRequiredLink("self").getHref() + "/deletefavorite")
                .withRel("deletefavorite")
        );

        return model;
    }
}
