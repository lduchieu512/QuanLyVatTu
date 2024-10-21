package com.example.medicalsupplieswebsite.service;

import com.example.medicalsupplieswebsite.entity.Account;

public interface IAccountService extends IService<Account> {

    public Account addAccount(Account account);

    public void setRoleForAccount(Long accountId, Long roleId);

    /*
     * NhanTQ
     * @param username
     * @param newPass
     */
    void changePassword(String username, String newPass);
}


