package aivle.infra.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import aivle.domain.command.AuthorSignupCommand;
import aivle.domain.entity.AuthorAccount;
import aivle.domain.repository.AuthorAccountRepository;

//<<< Clean Arch / Inbound Adaptor

@RestController
// @RequestMapping(value="/authorAccounts")
@Transactional
public class AuthorAccountController {

    @Autowired
    AuthorAccountRepository authorAccountRepository;

    @RequestMapping(
        value = "/authorAccounts/signup",
        method = RequestMethod.POST,
        produces = "application/json;charset=UTF-8"
    )
    public AuthorAccount signup(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody AuthorSignupCommand authorSignupCommand
    ) throws Exception {
        System.out.println(
            "##### /authorAccount/signup  called #####"
        );
        AuthorAccount authorAccount = new AuthorAccount();
        authorAccount.signup(
            authorSignupCommand
        );
        return authorAccount;
    }
}
//>>> Clean Arch / Inbound Adaptor
