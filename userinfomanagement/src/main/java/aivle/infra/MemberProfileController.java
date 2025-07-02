package aivle.infra;

import aivle.domain.*;
import java.util.Optional;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/memberProfiles")
@Transactional
public class MemberProfileController {

    @Autowired
    MemberProfileRepository memberProfileRepository;

    @PutMapping("/memberProfiles/{id}")
    public MemberProfile updateMemberProfile(
            @PathVariable Long id,
            @RequestBody Map<String, String> command
    ) {
        Optional<MemberProfile> optionalProfile = memberProfileRepository.findById(id);
        if (!optionalProfile.isPresent()) {
            throw new RuntimeException("MemberProfile not found");
        }
        MemberProfile profile = optionalProfile.get();
        profile.updateMemberProfile(
            command.get("name"),
            command.get("email"),
            command.get("roles"),
            command.get("basicInformation"),
            command.get("selfIntroduction")
        );
        return profile;
    }
}
//>>> Clean Arch / Inbound Adaptor
