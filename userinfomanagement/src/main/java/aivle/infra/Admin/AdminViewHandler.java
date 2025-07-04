package aivle.infra.Admin;

import aivle.domain.Admin.AdminProfileCreated;
import aivle.domain.Admin.AdminView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Service;

@Service
public class AdminViewHandler {

    @Autowired
    private AdminViewRepository adminViewRepository;

    @EventListener
    public void whenAdminProfileCreated_then_CreateAdminView(AdminProfileCreated adminProfileCreated) {
        AdminView adminView = new AdminView();
        adminView.setId(adminProfileCreated.getId());
        adminView.setName(adminProfileCreated.getName());
        
        adminViewRepository.save(adminView);
    }
}
