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
// @RequestMapping(value="/userAccounts")
@Transactional
public class UserAccountController {

    @Autowired
    UserAccountRepository userAccountRepository;

    @RequestMapping(
        value = "/userAccounts/{id}/signup",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public UserAccount signup(
        @PathVariable(value = "id") Long id,
        @RequestBody SignupCommand signupCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /userAccount/signup  called #####");
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(
            id
        );

        optionalUserAccount.orElseThrow(() -> new Exception("No Entity Found"));
        UserAccount userAccount = optionalUserAccount.get();
        userAccount.signup(signupCommand);

        userAccountRepository.save(userAccount);
        return userAccount;
    }

    @RequestMapping(
        value = "/userAccounts/{id}/logout",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )
    public UserAccount logout(
        @PathVariable(value = "id") Long id,
        @RequestBody LogoutCommand logoutCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /userAccount/logout  called #####");
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(
            id
        );

        optionalUserAccount.orElseThrow(() -> new Exception("No Entity Found"));
        UserAccount userAccount = optionalUserAccount.get();
        userAccount.logout(logoutCommand);

        userAccountRepository.delete(userAccount);
        return userAccount;
    }

    @RequestMapping(
        value = "/userAccounts/{id}/login",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public UserAccount login(
        @PathVariable(value = "id") Long id,
        @RequestBody LoginCommand loginCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /userAccount/login  called #####");
        Optional<UserAccount> optionalUserAccount = userAccountRepository.findById(
            id
        );

        optionalUserAccount.orElseThrow(() -> new Exception("No Entity Found"));
        UserAccount userAccount = optionalUserAccount.get();
        userAccount.login(loginCommand);

        userAccountRepository.save(userAccount);
        return userAccount;
    }
}
//>>> Clean Arch / Inbound Adaptor
