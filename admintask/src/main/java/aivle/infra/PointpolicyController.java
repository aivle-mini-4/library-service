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
@RequestMapping("/pointpolicies")
@Transactional
public class PointpolicyController {

    @Autowired
    private PointpolicyRepository pointpolicyRepository;

    // [CREATE] 포인트 정책 생성
    @PostMapping
    public Pointpolicy createPolicy(@RequestBody Pointpolicy policy) {
        return pointpolicyRepository.save(policy);
    }

    // [READ] 전체 포인트 정책 목록 조회
    @GetMapping
    public Iterable<Pointpolicy> getAllPolicies() {
        return pointpolicyRepository.findAll();
    }

    // [READ] 특정 포인트 정책 단건 조회
    @GetMapping("/{id}")
    public Pointpolicy getPolicy(@PathVariable Long id) {
        return pointpolicyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("정책을 찾을 수 없습니다: " + id));
    }

    // [UPDATE] 포인트 정책 수정
    @PutMapping("/{id}")
    public Pointpolicy updatePolicy(@PathVariable Long id, @RequestBody Pointpolicy updated) {
        return pointpolicyRepository.findById(id)
                .map(policy -> {
                    policy.setName(updated.getName());
                    policy.setDescription(updated.getDescription());
                    policy.setPointType(updated.getPointType());
                    policy.setAmount(updated.getAmount());
                    policy.setIsActive(updated.getIsActive());
                    // updatedAt은 @PreUpdate에서 자동 세팅됨
                    return pointpolicyRepository.save(policy);
                })
                .orElseThrow(() -> new RuntimeException("정책을 찾을 수 없습니다: " + id));
    }

    // [DELETE] 포인트 정책 삭제
    @DeleteMapping("/{id}")
    public void deletePolicy(@PathVariable Long id) {
        pointpolicyRepository.deleteById(id);
    }
}

//>>> Clean Arch / Inbound Adaptor
