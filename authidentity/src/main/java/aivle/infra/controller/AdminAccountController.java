package aivle.infra.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import aivle.domain.command.SignupCommand;
import aivle.domain.entity.AdminAccount;
import aivle.domain.repository.AdminAccountRepository;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/adminAccounts")
@Transactional
public class AdminAccountController {

    @Autowired
    AdminAccountRepository adminAccountRepository;

    @RequestMapping(
        value = "/adminAccounts/signup",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    @PreAuthorize("hasRole('ADMIN')")
    public AdminAccount signup(
        @RequestBody SignupCommand signupCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /adminAccount/signup  called #####");
        
        AdminAccount adminAccount = new AdminAccount();
        adminAccount.signup(signupCommand);

        adminAccountRepository.save(adminAccount);
        return adminAccount;
    }
}
//>>> Clean Arch / Inbound Adaptor
