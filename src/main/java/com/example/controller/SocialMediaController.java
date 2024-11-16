package com.example.controller;

import java.util.List;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.entity.Account;
import com.example.entity.Message;
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
    @Autowired
    private MessageService messageService;

    @RequestMapping(value = "/login", method = RequestMethod.POST) 
    public ResponseEntity<?> loginAccountHandler(@RequestBody Account account) {
        Account loggedInAccount = accountService.loginAccount(account);
        return ResponseEntity.status(200).body(loggedInAccount);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<?> registerAccountHandler(@RequestBody Account account) {
        Account registeredAccount = accountService.registerAccount(account);
        return ResponseEntity.status(200).body(registeredAccount);
    }

    @RequestMapping(value = "/messages", method = RequestMethod.POST)
    public ResponseEntity<?> createMessageHandler(@RequestBody Message message) {
        Message createdMessage = messageService.createNewMessage(message);
        return ResponseEntity.status(200).body(createdMessage);
    }

    @RequestMapping(value = "/messages", method = RequestMethod.GET)
    public ResponseEntity<?> retrieveAllMessagesHandler() {
        List<Message> allMessages = messageService.getAllMessages();
        return ResponseEntity.status(200).body(allMessages);
    }

    @RequestMapping(value = "/messages/{messageId}", method = RequestMethod.GET)
    public ResponseEntity<?> retrieveMessageByIDHandler(@PathVariable int messageId) {
        Message retrievedMessage = messageService.getMessageByID(messageId);
        return ResponseEntity.status(200).body(retrievedMessage);
    }

    @RequestMapping(value = "/messages/{messageId}", method = RequestMethod.DELETE) 
    public ResponseEntity<?> deleteMessageHandler(@PathVariable int messageId) {
        return ResponseEntity.status(200).body(null);
    }

    @RequestMapping(value = "/messages/{messageId}", method = RequestMethod.PATCH) 
    public ResponseEntity<?> updateMessageHandler(@PathVariable int messageId, @RequestBody Message message) {
        int updatedMessageRowNumber = messageService.updateMessage(messageId, message);
        return ResponseEntity.status(200).body(updatedMessageRowNumber);
    }

    @RequestMapping(value = "/accounts/{accountId}/messages", method = RequestMethod.GET)
    public ResponseEntity<?> getMessagesByUser(@PathVariable int accountId) {
       List<Message> userMessages = messageService.getMessagesByUser(accountId);
       return ResponseEntity.status(200).body(userMessages);
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