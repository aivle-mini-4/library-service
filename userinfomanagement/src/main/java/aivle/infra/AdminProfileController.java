package aivle.infra;

import aivle.domain.*;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/adminProfiles")
@Transactional
public class AdminProfileController {

    @Autowired
    AdminProfileRepository adminProfileRepository;
}
//>>> Clean Arch / Inbound Adaptor
