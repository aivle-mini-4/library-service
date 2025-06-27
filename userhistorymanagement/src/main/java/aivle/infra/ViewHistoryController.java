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
// @RequestMapping(value="/viewHistories")
@Transactional
public class ViewHistoryController {

    @Autowired
    ViewHistoryRepository viewHistoryRepository;

    @RequestMapping(
        value = "/viewHistories/{id}/registerviewhistory",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public ViewHistory registerViewHistory(
        @PathVariable(value = "id") Long id,
        @RequestBody RegisterViewHistoryCommand registerViewHistoryCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println(
            "##### /viewHistory/registerViewHistory  called #####"
        );
        Optional<ViewHistory> optionalViewHistory = viewHistoryRepository.findById(
            id
        );

        optionalViewHistory.orElseThrow(() -> new Exception("No Entity Found"));
        ViewHistory viewHistory = optionalViewHistory.get();
        viewHistory.registerViewHistory(registerViewHistoryCommand);

        viewHistoryRepository.save(viewHistory);
        return viewHistory;
    }
}
//>>> Clean Arch / Inbound Adaptor
