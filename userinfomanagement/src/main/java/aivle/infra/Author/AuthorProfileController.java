package aivle.infra.Author;

import aivle.domain.Author.AuthorProfile;
import aivle.domain.Author.AuthorProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class AuthorProfileController {

    @Autowired
    private AuthorProfileRepository authorProfileRepository;

    @PutMapping("/authorProfile")
    public ResponseEntity<AuthorProfile> updateAuthorProfile(
            @RequestHeader(value = "X-User-Id", required = false) Long id,
            @RequestHeader(value = "X-User-Role", required = false) String role,
            @RequestBody Map<String, String> command
    ) {
        // 권한 확인 - AUTHOR 또는 ADMIN만 접근 가능
        if (id == null || role == null || 
            (!"AUTHOR".equals(role) && !"ADMIN".equals(role))) {
            return ResponseEntity.status(403).build();
        }

        Optional<AuthorProfile> authorProfileOpt = authorProfileRepository.findById(id);
        if (authorProfileOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AuthorProfile authorProfile = authorProfileOpt.get();
        
        // 요청된 필드들 업데이트
        if (command.containsKey("name")) {
            authorProfile.setName(command.get("name"));
        }
        if (command.containsKey("email")) {
            authorProfile.setEmail(command.get("email"));
        }
        if (command.containsKey("basicInformation")) {
            authorProfile.setBasicInformation(command.get("basicInformation"));
        }
        if (command.containsKey("selfIntroduction")) {
            authorProfile.setSelfIntroduction(command.get("selfIntroduction"));
        }
        if (command.containsKey("portfolio")) {
            authorProfile.setPortfolio(command.get("portfolio"));
        }

        // 도메인 메서드를 사용하여 업데이트
        authorProfile.updateAuthorProfile(
            authorProfile.getName(),
            authorProfile.getEmail(),
            authorProfile.getBasicInformation(),
            authorProfile.getSelfIntroduction(),
            authorProfile.getPortfolio()
        );

        return ResponseEntity.ok(authorProfile);
    }

    @GetMapping("/authorProfile")
    public ResponseEntity<AuthorProfile> getAuthorProfile(
            @RequestHeader(value = "X-User-Id", required = false) Long id,
            @RequestHeader(value = "X-User-Role", required = false) String role
    ) {
        // 권한 확인 - AUTHOR 또는 ADMIN만 접근 가능
        if (id == null || role == null || 
            (!"AUTHOR".equals(role) && !"ADMIN".equals(role))) {
            return ResponseEntity.status(403).build();
        }

        Optional<AuthorProfile> authorProfile = authorProfileRepository.findById(id);
        return authorProfile.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
