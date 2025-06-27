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
// @RequestMapping(value="/adminAccounts")
@Transactional
public class AdminAccountController {

    @Autowired
    AdminAccountRepository adminAccountRepository;

    @RequestMapping(
        value = "/adminAccounts/{id}/signup",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public AdminAccount signup(
        @PathVariable(value = "id") Long id,
        @RequestBody SignupCommand signupCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /adminAccount/signup  called #####");
        Optional<AdminAccount> optionalAdminAccount = adminAccountRepository.findById(
            id
        );

        optionalAdminAccount.orElseThrow(() -> new Exception("No Entity Found")
        );
        AdminAccount adminAccount = optionalAdminAccount.get();
        adminAccount.signup(signupCommand);

        adminAccountRepository.save(adminAccount);
        return adminAccount;
    }

    @RequestMapping(
        value = "/adminAccounts/login",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public AdminAccount login(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody LoginCommand loginCommand
    ) throws Exception {
        System.out.println("##### /adminAccount/login  called #####");
        AdminAccount adminAccount = new AdminAccount();
        adminAccount.login(loginCommand);
        adminAccountRepository.save(adminAccount);
        return adminAccount;
    }

    @RequestMapping(
        value = "/adminAccounts/{id}/logout",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )
    public AdminAccount logout(
        @PathVariable(value = "id") Long id,
        @RequestBody LogoutCommand logoutCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /adminAccount/logout  called #####");
        Optional<AdminAccount> optionalAdminAccount = adminAccountRepository.findById(
            id
        );

        optionalAdminAccount.orElseThrow(() -> new Exception("No Entity Found")
        );
        AdminAccount adminAccount = optionalAdminAccount.get();
        adminAccount.logout(logoutCommand);

        adminAccountRepository.delete(adminAccount);
        return adminAccount;
    }
}
//>>> Clean Arch / Inbound Adaptor
