package aivle.infra.Member;

import aivle.domain.Member.MemberProfile;
import aivle.domain.Member.MemberProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class MemberProfileController {

    @Autowired
    private MemberProfileRepository memberProfileRepository;

    @PutMapping("/memberProfile")
    public ResponseEntity<MemberProfile> updateMemberProfile(
            @RequestHeader(value = "X-User-Id", required = false) Long id,
            @RequestHeader(value = "X-User-Role", required = false) String role,
            @RequestBody Map<String, String> command
    ) {
        // 권한 확인 - MEMBER, AUTHOR, ADMIN 모두 접근 가능
        if (id == null || role == null || 
            (!"MEMBER".equals(role) && !"AUTHOR".equals(role) && !"ADMIN".equals(role))) {
            return ResponseEntity.status(403).build();
        }

        Optional<MemberProfile> memberProfileOpt = memberProfileRepository.findById(id);
        if (memberProfileOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        MemberProfile memberProfile = memberProfileOpt.get();
        
        // 요청된 필드들 업데이트
        String name = memberProfile.getName();
        String email = memberProfile.getEmail();
        String basicInformation = memberProfile.getBasicInformation();
        
        if (command.containsKey("name")) {
            name = command.get("name");
        }
        if (command.containsKey("email")) {
            email = command.get("email");
        }
        if (command.containsKey("basicInformation")) {
            basicInformation = command.get("basicInformation");
        }

        // 도메인 메서드를 사용하여 업데이트
        memberProfile.updateMemberProfile(name, email, basicInformation);

        return ResponseEntity.ok(memberProfile);
    }

    @GetMapping("/memberProfile")
    public ResponseEntity<MemberProfile> getMemberProfile(
            @RequestHeader(value = "X-User-Id", required = false) Long id,
            @RequestHeader(value = "X-User-Role", required = false) String role
    ) {
        // 권한 확인 - MEMBER, AUTHOR, ADMIN 모두 접근 가능
        if (id == null || role == null || 
            (!"MEMBER".equals(role) && !"AUTHOR".equals(role) && !"ADMIN".equals(role))) {
            return ResponseEntity.status(403).build();
        }

        Optional<MemberProfile> memberProfile = memberProfileRepository.findById(id);
        return memberProfile.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/memberProfile/{memberId}")
    public ResponseEntity<MemberProfile> getMemberProfileById(
            @PathVariable Long memberId,
            @RequestHeader(value = "X-User-Role", required = false) String role
    ) {
        // ADMIN만 다른 회원의 프로필을 조회할 수 있음
        if (role == null || !"ADMIN".equals(role)) {
            return ResponseEntity.status(403).build();
        }

        Optional<MemberProfile> memberProfile = memberProfileRepository.findById(memberId);
        return memberProfile.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/memberProfiles")
    public ResponseEntity<Iterable<MemberProfile>> getAllMemberProfiles(
            @RequestHeader(value = "X-User-Role", required = false) String role
    ) {
        // ADMIN만 모든 회원 프로필을 조회할 수 있음
        if (role == null || !"ADMIN".equals(role)) {
            return ResponseEntity.status(403).build();
        }

        Iterable<MemberProfile> memberProfiles = memberProfileRepository.findAll();
        return ResponseEntity.ok(memberProfiles);
    }
}
