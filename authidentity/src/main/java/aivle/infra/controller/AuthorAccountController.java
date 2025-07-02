package aivle.infra.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
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
    public ResponseEntity<?> signup(
        HttpServletRequest request,
        HttpServletResponse response,
        @RequestBody AuthorSignupCommand authorSignupCommand
    ) throws Exception {
        System.out.println(
            "##### /authorAccount/signup  called #####"
        );
        try {
            AuthorAccount authorAccount = new AuthorAccount();
            authorAccount.signup(
                authorSignupCommand
            );
            return ResponseEntity.ok(authorAccount);
        } catch (IllegalArgumentException e) {
            Map<String, String> errorResponse = new HashMap<>();
            errorResponse.put("error", e.getMessage());
            return ResponseEntity.badRequest().body(errorResponse);
        }
    }
    
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<Map<String, String>> handleIllegalArgumentException(IllegalArgumentException e) {
        Map<String, String> errorResponse = new HashMap<>();
        errorResponse.put("error", e.getMessage());
        return ResponseEntity.badRequest().body(errorResponse);
    }
}
//>>> Clean Arch / Inbound Adaptor
