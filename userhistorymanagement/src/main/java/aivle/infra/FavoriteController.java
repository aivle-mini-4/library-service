package aivle.infra;

import aivle.domain.*;
import aivle.infra.QueryFavoriteListRepository;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/favorites")
@Transactional
public class FavoriteController {

    @Autowired
    FavoriteRepository favoriteRepository;

    @Autowired
    QueryFavoriteListRepository queryFavoriteListRepository;

    @RequestMapping(
        value = "/favorites/registerfavorite",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )

    @PostMapping
    public Favorite registerFavorite(
        @RequestHeader(value = "X-User-Id") Long userId,
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody RegisterFavoriteCommand registerFavoriteCommand
    ) throws Exception {
        System.out.println("##### /favorite/registerFavorite called #####");

        registerFavoriteCommand.setUserId(userId);
        Favorite favorite = new Favorite();
        favorite.registerFavorite(registerFavoriteCommand);
        favoriteRepository.save(favorite);

        return favorite;
    }


    @RequestMapping(
        value = "/favorites/deletefavorite/{id}",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )

    @DeleteMapping("/{id}")
    public Favorite deleteFavorite(
        @PathVariable(value = "id") Long id,
        @RequestHeader(value = "X-User-Id") Long userId,
        @RequestBody DeleteFavoriteCommand deleteFavoriteCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /favorite/deleteFavorite called #####");
        Optional<Favorite> optionalFavorite = favoriteRepository.findById(id);

        deleteFavoriteCommand.setUserId(userId);

        optionalFavorite.orElseThrow(() -> new Exception("No Entity Found"));
        Favorite favorite = optionalFavorite.get();
        favorite.deleteFavorite(deleteFavoriteCommand);

        favoriteRepository.delete(favorite);
        return favorite;
    }


    @GetMapping("/favorites")
    public List<QueryFavoriteList> getAllFavorites(
        @RequestHeader("X-User-Id") Long userId
    ) {
        return queryFavoriteListRepository.findByUserId(userId);
    }
}
//>>> Clean Arch / Inbound Adaptor
