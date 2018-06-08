package com.axmor.db.repository;

import com.axmor.db.entityes.Account;


public class AccountRepository extends EntityRepository<Long, Account>{

    public Account findByLogin(String login){
        Account account = (Account) openSession().createQuery("from Account where (login = '" + login + "')").uniqueResult();
        closeCurrentSession();
        return account;
    }

    public Account findByEmail(String email){
        Account account = (Account) openSession().createQuery("from Account where (email = '" + email + "')").uniqueResult();
        closeCurrentSession();
        return account;
    }

    @Override
    public String getEntityName() {
        return Account.class.getSimpleName();
    }

    @Override
    public Class<Account> getEntityType() {
        return Account.class;
    }
}
