package com.axmor.util;

import com.axmor.db.entityes.Account;
import com.axmor.db.entityes.Issue;
import com.axmor.db.entityes.enums.Role;
import com.axmor.db.entityes.enums.Status;
import com.axmor.db.repository.AccountRepository;
import com.axmor.db.repository.IssueRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.Date;

@RequiredArgsConstructor
public class DefaultDataInit {

    private AccountRepository accountRepository = new AccountRepository();
    private IssueRepository issueRepository = new IssueRepository();

    public void createDefaultData(){
        if(accountRepository.findByLogin("admin")!= null){
            return;
        }
        Account account = new Account();
        account.setLogin("admin");
        account.setPassword(DigestUtils.md5Hex("admin"));
        account.setEmail("admin@dot.com");
        account.setRole(Role.ROLE_ADMIN);
        account.setCreated(new Date());
        account.setModified(new Date());
        accountRepository.save(account);

        account = new Account();
        account.setLogin("igor");
        account.setPassword(DigestUtils.md5Hex("igor"));
        account.setEmail("igor@dot.com");
        account.setRole(Role.ROLE_MANAGER);
        account.setCreated(new Date());
        account.setModified(new Date());
        accountRepository.save(account);

        Issue issue= new Issue();
        issue.setTitle("First isuue title");
        issue.setDescription("First isuue description. Just for test app. ");
        issue.setStatus(Status.Created);
        issue.setCreated(new Date());
        issue.setModified(new Date());
        issue.setAccount(accountRepository.findByLogin(account.getLogin()));
        issueRepository.save(issue);

        issue= new Issue();
        issue.setTitle("Second isuue title");
        issue.setDescription("Second isuue description. Please, update description");
        issue.setStatus(Status.Created);
        issue.setCreated(new Date());
        issue.setModified(new Date());
        issue.setAccount(accountRepository.findByLogin(account.getLogin()));
        issueRepository.save(issue);
    }
}
