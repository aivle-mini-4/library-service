package aivle.infra;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import aivle.domain.*;
import java.util.Optional;
import java.time.LocalDateTime;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//<<< Clean Arch / Inbound Adaptor

@RestController
@RequestMapping("/authorapprovals")
@Transactional
public class AuthorapprovalController {

    @Autowired
    private AuthorapprovalRepository authorapprovalRepository;

    // 작가 승인 요청 생성
    @PostMapping
    public Authorapproval createApproval(@RequestBody Authorapproval approval) {
        approval.setState(ApprovalState.PENDING);
        approval.setAppliedAt(LocalDateTime.now());
        return authorapprovalRepository.save(approval);
    }

    // 전체 승인 요청 목록 조회
    @GetMapping
    public Iterable<Authorapproval> getAllApprovals() {
        return authorapprovalRepository.findAll();
    }

    //  단일 승인 요청 조회
    @GetMapping("/{id}")
    public Authorapproval getApproval(@PathVariable Long id) {
        return authorapprovalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("승인 요청이 존재하지 않습니다: " + id));
    }

    //  승인 처리
    @PutMapping("/{id}/approve")
    public Authorapproval approve(@PathVariable Long id, @RequestParam Long adminId) {
        Authorapproval approval = authorapprovalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("승인 요청이 존재하지 않습니다: " + id));
        approval.approve(adminId);
        return authorapprovalRepository.save(approval);
    }

    //거부 처리
    @PutMapping("/{id}/reject")
    public Authorapproval reject(@PathVariable Long id, @RequestParam Long adminId, @RequestParam String reason) {
        Authorapproval approval = authorapprovalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("승인 요청이 존재하지 않습니다: " + id));
        approval.reject(adminId, reason);
        return authorapprovalRepository.save(approval);
    }
}

//>>> Clean Arch / Inbound Adaptor
