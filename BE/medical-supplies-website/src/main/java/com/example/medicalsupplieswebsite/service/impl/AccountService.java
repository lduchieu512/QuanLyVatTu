package com.example.medicalsupplieswebsite.service.impl;

import com.example.medicalsupplieswebsite.entity.Account;
import com.example.medicalsupplieswebsite.entity.Role;
import com.example.medicalsupplieswebsite.repository.IAccountRepository;
import com.example.medicalsupplieswebsite.repository.IRoleRepository;
import com.example.medicalsupplieswebsite.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AccountService implements IAccountService {
    private final IAccountRepository accountRepository;
    private final IRoleRepository roleRepository;

    public AccountService(IAccountRepository accountRepository, IRoleRepository roleRepository) {
        this.accountRepository = accountRepository;
        this.roleRepository = roleRepository;
    }

    public Account addAccount(Account account) {
        if (accountRepository.existsByUsername(account.getUsername())) {
            throw new IllegalArgumentException("Tên tài khoản đã tồn tại. Vui lòng chọn tên tài khoản khác.");
        }
        if (accountRepository.existsByEmail(account.getEmail())) {
            throw new IllegalArgumentException("Email đã tồn tại. Vui lòng chọn email khác.");
        }
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encodedPassword = passwordEncoder.encode(account.getEncryptPassword());
        account.setEncryptPassword(encodedPassword);
        account.setEnable(true);

        return account;
    }


    public void setRoleForAccount(Long accountId, Long roleId) {
        Account account = accountRepository.findById(accountId).orElse(null);
        if (account != null) {
            Role role = roleRepository.findById(roleId).orElse(null);
            if (role != null) {
                accountRepository.setRoleForAccount(accountId, roleId);
            }
        }

    }

    @Override
    public Page<Account> findAll(Pageable pageable) {
        return null;
    }

    @Override
    public Account findById(Long id) {
        return null;
    }

    @Override
    public Account update(Account account) {
        return null;
    }

    @Override
    public void deleteById(Long id) {

    }

    /*
     * NhanTQ
     * @param username
     * @param newPass
     */
    @Override
    public void changePassword(String username, String newPass) {
        accountRepository.changePassword(username,newPass);
    }

    public boolean existsByUsername(String username) {
        return accountRepository.existsByUsername(username);
    }

    public boolean existsByEmail(String email) {
        return accountRepository.existsByEmail(email);
    }
}
