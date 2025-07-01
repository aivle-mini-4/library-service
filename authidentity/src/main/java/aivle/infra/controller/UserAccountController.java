package aivle.infra.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import aivle.domain.command.SignupCommand;
import aivle.domain.entity.UserAccount;
import aivle.domain.repository.UserAccountRepository;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/userAccounts")
@Transactional
public class UserAccountController {

    @Autowired
    UserAccountRepository userAccountRepository;

    @RequestMapping(
        value = "/userAccounts/signup",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    // @PreAuthorize("hasRole('USER') or hasRole('ADMIN')") // 회원가입은 권한 불필요
    public UserAccount signup(
        @RequestBody SignupCommand signupCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /userAccount/signup  called #####");
        
        UserAccount userAccount = new UserAccount();
        userAccount.signup(signupCommand);

        userAccountRepository.save(userAccount);
        return userAccount;
    }
}
//>>> Clean Arch / Inbound Adaptor
