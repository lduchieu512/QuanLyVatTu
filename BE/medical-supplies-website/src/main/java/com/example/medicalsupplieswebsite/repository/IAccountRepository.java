package com.example.medicalsupplieswebsite.repository;

import com.example.medicalsupplieswebsite.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import javax.transaction.Transactional;
import java.util.Optional;
@Transactional
public interface IAccountRepository extends JpaRepository<Account, Long> {

    @Query("SELECT account FROM Account account WHERE account.username = ?1")
    Account findByUserName(String userName);

    @Query(value = "INSERT INTO account_roles (account_id, role_id) " +
            "VALUES (:accountId, :roleId)", nativeQuery = true)
    @Modifying
    void setRoleForAccount(@Param("accountId") Long accountId, @Param("roleId") Long roleId);

    /*
     * ThanhNV - login, xac thuc va phan quyen
     */
    @Query(nativeQuery = true, value =
            "select account_id, username, email, encrypt_password, is_enable " +
                    "from account " +
                    "where username = :username and is_enable = true")
    Optional<Account> findAccountByUsername(@Param("username") String username);

    boolean existsByUsername(String username);
    boolean existsByEmail(String email);

    /*
     * NhanTQ
     * @param username
     * @param pass -> new pass
     */
    @Modifying
    @Query(value = "update `account` a set a.encrypt_password =:pass " +
            "where (a.is_enable = true) and (username = :username) ",
            nativeQuery = true)
    void changePassword(@Param("username") String username,
                        @Param("pass") String pass);
    @Modifying
    @Query(value = "update `account` a " +
            "set a.is_enable = false " +
            "where a.account_id = :id",
            nativeQuery = true)
    void deleteById(@Param("id") Long id);
}


