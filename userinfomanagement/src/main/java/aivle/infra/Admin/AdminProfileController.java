package aivle.infra.Admin;

import aivle.domain.Admin.AdminProfile;
import aivle.domain.Admin.AdminProfileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.Optional;

@RestController
public class AdminProfileController {

    @Autowired
    private AdminProfileRepository adminProfileRepository;

    @PutMapping("/adminProfile")
    public ResponseEntity<AdminProfile> updateAdminProfile(
            @RequestHeader(value = "X-User-Id", required = false) Long id,
            @RequestHeader(value = "X-User-Role", required = false) String role,
            @RequestBody Map<String, String> command
    ) {
        // 권한 확인
        if (id == null || role == null || !"ADMIN".equals(role)) {
            return ResponseEntity.status(403).build();
        }

        Optional<AdminProfile> adminProfileOpt = adminProfileRepository.findById(id);
        if (adminProfileOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        AdminProfile adminProfile = adminProfileOpt.get();
        
        // 요청된 필드들 업데이트
        if (command.containsKey("name")) {
            adminProfile.setName(command.get("name"));
        }
        if (command.containsKey("email")) {
            adminProfile.setEmail(command.get("email"));
        }

        AdminProfile updatedProfile = adminProfileRepository.save(adminProfile);
        return ResponseEntity.ok(updatedProfile);
    }

    @GetMapping("/adminProfile")
    public ResponseEntity<AdminProfile> getAdminProfile(
            @RequestHeader(value = "X-User-Id", required = false) Long id,
            @RequestHeader(value = "X-User-Role", required = false) String role
    ) {
        // 권한 확인
        if (id == null || role == null || !"ADMIN".equals(role)) {
            return ResponseEntity.status(403).build();
        }

        Optional<AdminProfile> adminProfile = adminProfileRepository.findById(id);
        return adminProfile.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
