package aivle.infra;

import aivle.domain.*;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/favorites")
@Transactional
public class FavoriteController {

    @Autowired
    FavoriteRepository favoriteRepository;

    @RequestMapping(
        value = "/favorites/{id}/registerfavorite",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public Favorite registerFavorite(
        @PathVariable(value = "id") Long id,
        @RequestBody RegisterFavoriteCommand registerFavoriteCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /favorite/registerFavorite  called #####");
        Optional<Favorite> optionalFavorite = favoriteRepository.findById(id);

        optionalFavorite.orElseThrow(() -> new Exception("No Entity Found"));
        Favorite favorite = optionalFavorite.get();
        favorite.registerFavorite(registerFavoriteCommand);

        favoriteRepository.save(favorite);
        return favorite;
    }

    @RequestMapping(
        value = "/favorites/{id}/deletefavorite",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )
    public Favorite deleteFavorite(
        @PathVariable(value = "id") Long id,
        @RequestBody DeleteFavoriteCommand deleteFavoriteCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /favorite/deleteFavorite  called #####");
        Optional<Favorite> optionalFavorite = favoriteRepository.findById(id);

        optionalFavorite.orElseThrow(() -> new Exception("No Entity Found"));
        Favorite favorite = optionalFavorite.get();
        favorite.deleteFavorite(deleteFavoriteCommand);

        favoriteRepository.delete(favorite);
        return favorite;
    }
}
//>>> Clean Arch / Inbound Adaptor
