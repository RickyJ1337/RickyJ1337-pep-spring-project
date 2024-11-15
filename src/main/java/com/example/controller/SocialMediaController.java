package com.example.controller;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.function.ServerResponse.Context;

import com.example.entity.Account;
import com.example.exception.LoginFailureException;
import com.example.service.AccountService;
import com.example.service.MessageService;
/**
 * TODO: You will need to write your own endpoints and handlers for your controller using Spring. The endpoints you will need can be
 * found in readme.md as well as the test cases. You be required to use the @GET/POST/PUT/DELETE/etc Mapping annotations
 * where applicable as well as the @ResponseBody and @PathVariable annotations. You should
 * refer to prior mini-project labs and lecture materials for guidance on how a controller may be built.
 */
@Controller
public class SocialMediaController {

    @Autowired
    private AccountService accountService;
    private MessageService messageService;

    @RequestMapping(value = "/login", method = RequestMethod.POST) 
    public ResponseEntity<?> loginAccountHandler (@RequestBody Account account) {
        Account loggedInAccount = accountService.loginAccount(account);
        return ResponseEntity.status(200).body(loggedInAccount);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerAccountHandler (@RequestBody Account account) {
        Account registeredAccount = accountService.registerAccount(account);
        return ResponseEntity.status(201).body(registeredAccount);
    }

    @ExceptionHandler(EntityExistsException.class) 
    public ResponseEntity<?> accountExistsExceptionHandler(EntityExistsException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.CONFLICT);
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<?> illegalArgumentExceptionHandler(IllegalArgumentException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(LoginFailureException.class)
    public ResponseEntity<?> loginFailureExceptionHandler(LoginFailureException ex) {
        return new ResponseEntity<>(ex.getMessage(), HttpStatus.UNAUTHORIZED);
    }
}