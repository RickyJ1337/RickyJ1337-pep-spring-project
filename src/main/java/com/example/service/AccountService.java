package com.example.service;

import com.example.entity.Account;
import com.example.exception.LoginFailureException;
import com.example.repository.AccountRepository;

import javax.persistence.EntityExistsException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountService {
    @Autowired
    private AccountRepository accountRepository;

    public Account loginAccount(Account account) {
        Account loggedInAccount = accountRepository.findAccount(account.getUsername(), account.getPassword());
        if (loggedInAccount == null) {throw new LoginFailureException("Unsuccessful login, try again.");}
        return loggedInAccount;
    }

    @Transactional
    public Account registerAccount(Account account) {
        String username = account.getUsername();
        String password = account.getPassword();

        if (username == "") {throw new IllegalArgumentException("Username is blank");}
        if (password.length() < 4) {throw new IllegalArgumentException("Password length is too short");}
        if (accountRepository.findAccountByUsername(username) != null) {throw new EntityExistsException("Username already exists!");}
        return accountRepository.save(account);
    }
}
