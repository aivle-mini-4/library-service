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
// @RequestMapping(value="/writerProfiles")
@Transactional
public class WriterProfileController {

    @Autowired
    WriterProfileRepository writerProfileRepository;

    @PutMapping("/writerProfiles/{id}")
    public WriterProfile updateWriterProfile(
            @PathVariable Long id,
            @RequestBody Map<String, String> command
    ) {
        Optional<WriterProfile> optionalProfile = writerProfileRepository.findById(id);
        if (!optionalProfile.isPresent()) {
            throw new RuntimeException("WriterProfile not found");
        }
        WriterProfile profile = optionalProfile.get();
        profile.updateWriterProfile(
            command.get("name"),
            command.get("email"),
            command.get("roles"),
            command.get("basicInformation"),
            command.get("selfIntroduction"),
            command.get("portfolio")
        );
        return profile;
    }
}
//>>> Clean Arch / Inbound Adaptor
