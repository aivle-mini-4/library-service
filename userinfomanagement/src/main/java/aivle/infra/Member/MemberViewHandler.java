package aivle.infra.Member;

import aivle.domain.Member.MemberProfileCreated;
import aivle.domain.Member.MemberProfileUpdated;
import aivle.domain.Member.MemberView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class MemberViewHandler {

    @Autowired
    private MemberViewRepository memberViewRepository;

    @EventListener
    public void whenMemberProfileCreated_then_CreateMemberView(MemberProfileCreated memberProfileCreated) {
        MemberView memberView = new MemberView();
        memberView.setId(memberProfileCreated.getId());
        memberView.setName(memberProfileCreated.getName());
        memberView.setEmail(memberProfileCreated.getEmail());
        memberView.setBasicInfo(memberProfileCreated.getBasicInformation());
        memberView.setLastUpdatedAt(memberProfileCreated.getUpdatedAt());
        memberView.setJoinedAt(memberProfileCreated.getUpdatedAt());
        
        memberViewRepository.save(memberView);
    }

    @EventListener
    public void whenMemberProfileUpdated_then_UpdateMemberView(MemberProfileUpdated memberProfileUpdated) {
        MemberView memberView = memberViewRepository.findById(memberProfileUpdated.getId())
            .orElse(new MemberView());
        
        memberView.setId(memberProfileUpdated.getId());
        memberView.setName(memberProfileUpdated.getName());
        memberView.setEmail(memberProfileUpdated.getEmail());
        memberView.setBasicInfo(memberProfileUpdated.getBasicInformation());
        memberView.setLastUpdatedAt(memberProfileUpdated.getUpdatedAt());
        
        memberViewRepository.save(memberView);
    }
}
