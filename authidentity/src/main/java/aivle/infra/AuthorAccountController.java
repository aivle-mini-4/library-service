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
// @RequestMapping(value="/authorAccounts")
@Transactional
public class AuthorAccountController {

    @Autowired
    AuthorAccountRepository authorAccountRepository;

    @RequestMapping(
        value = "/authorAccounts/requestauthorregistration",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public AuthorAccount requestAuthorRegistration(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody RequestAuthorRegistrationCommand requestAuthorRegistrationCommand
    ) throws Exception {
        System.out.println(
            "##### /authorAccount/requestAuthorRegistration  called #####"
        );
        AuthorAccount authorAccount = new AuthorAccount();
        authorAccount.requestAuthorRegistration(
            requestAuthorRegistrationCommand
        );
        authorAccountRepository.save(authorAccount);
        return authorAccount;
    }

    @RequestMapping(
        value = "/authorAccounts/{id}/logout",
        method = RequestMethod.DELETE,
        produces = "application/json;charset=UTF-8"
    )
    public AuthorAccount logout(
        @PathVariable(value = "id") Long id,
        @RequestBody LogoutCommand logoutCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /authorAccount/logout  called #####");
        Optional<AuthorAccount> optionalAuthorAccount = authorAccountRepository.findById(
            id
        );

        optionalAuthorAccount.orElseThrow(() -> new Exception("No Entity Found")
        );
        AuthorAccount authorAccount = optionalAuthorAccount.get();
        authorAccount.logout(logoutCommand);

        authorAccountRepository.delete(authorAccount);
        return authorAccount;
    }

    @RequestMapping(
        value = "/authorAccounts/{id}/login",
        method = RequestMethod.PUT,
        produces = "application/json;charset=UTF-8"
    )
    public AuthorAccount login(
        @PathVariable(value = "id") Long id,
        @RequestBody LoginCommand loginCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /authorAccount/login  called #####");
        Optional<AuthorAccount> optionalAuthorAccount = authorAccountRepository.findById(
            id
        );

        optionalAuthorAccount.orElseThrow(() -> new Exception("No Entity Found")
        );
        AuthorAccount authorAccount = optionalAuthorAccount.get();
        authorAccount.login(loginCommand);

        authorAccountRepository.save(authorAccount);
        return authorAccount;
    }
}
//>>> Clean Arch / Inbound Adaptor
