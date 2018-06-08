package com.axmor.services;

import com.axmor.db.entityes.Account;
import com.axmor.db.entityes.enums.Role;
import com.axmor.db.repository.AccountRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;
import spark.Request;

import java.util.Date;

/**
 * AccountService is repository for working with user data.
 * Here we're creating and check user accounts
 */
@RequiredArgsConstructor
public class AccountService {

    private final AccountRepository accountRepository;

    public ServiceMessages registration(Request request){

        Account account = constructAccount(request);

        /**
         * This block validate request data fields.
         */
        if(account.getLogin().isEmpty()){
            return new ServiceMessages(StatusMessage.error, "Empty login. Try again");
        }else if(account.getEmail().isEmpty()){
            return new ServiceMessages(StatusMessage.error, "Empty email. Try again");
        }else if(account.getPassword().isEmpty()){
            return new ServiceMessages(StatusMessage.error, "Incorrect password. Try again");
        }

        if(accountRepository.findByLogin(account.getLogin()) != null){
            return new ServiceMessages(StatusMessage.error, "This login exists. Try again");
        }
        if(accountRepository.findByEmail(account.getEmail()) != null){
            return new ServiceMessages(StatusMessage.error, "User with this email exists. Try again");
        }

        accountRepository.save(account);
        // Creating session
        request.session(true);
        request.session().attribute("login", account.getLogin());
        request.session().attribute("role", account.getRole());

        return new ServiceMessages(StatusMessage.ok, "Congrat! Account successful created!");
    }

    public ServiceMessages autorisation(Request request){
        Account account = accountRepository.findByLogin(request.queryParams("login").trim());

        if(account == null){
            return new ServiceMessages(StatusMessage.error,"Incorrect login");
        }

        if(!account.getPassword().equals(getMD5Password(request.queryParams("password")))){
            return new ServiceMessages(StatusMessage.error,"Incorrect password! Try again!");
        }

        // Creating session
        request.session(true);
        request.session().attribute("login", account.getLogin());
        request.session().attribute("role", account.getRole());
        request.session().attribute("id_user", account.getId());
        return new ServiceMessages(StatusMessage.ok,"Welcome!");
    }

    private Account constructAccount(Request request){
        String login = request.queryParams("login").trim();
        String password = getMD5Password(request.queryParams("password").trim());
        String email = request.queryParams("email").trim();
        Account account = new Account();
        account.setLogin(login);
        account.setEmail(email);
        account.setPassword(password);
        account.setRole(Role.ROLE_MANAGER);
        account.setCreated(new Date());
        account.setModified(new Date());
        return account;
    }

    private String getMD5Password(String password){
        return DigestUtils.md5Hex(password);
    }

    public void logout(Request request) {
        request.session().removeAttribute("login");
        request.session().removeAttribute("role");
        request.session().removeAttribute("id_user");
    }
}
