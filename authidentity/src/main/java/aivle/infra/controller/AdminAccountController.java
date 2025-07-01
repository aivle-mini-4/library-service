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
    public ResponseEntity<?> signup(
        @RequestBody SignupCommand signupCommand,
        HttpServletRequest request,
        HttpServletResponse response
    ) throws Exception {
        System.out.println("##### /adminAccount/signup  called #####");
        
        try {
            AdminAccount adminAccount = new AdminAccount();
            adminAccount.signup(signupCommand);

            adminAccountRepository.save(adminAccount);
            return ResponseEntity.ok(adminAccount);
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
