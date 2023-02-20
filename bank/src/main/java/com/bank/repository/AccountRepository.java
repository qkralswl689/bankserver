package com.bank.repository;

import com.bank.entity.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountRepository extends JpaRepository<Account, Long> {

    //JPQL사용
    @Query("select a from Account a " +
            "where a.member.email = :email")
    List<Account> findByEmail(@Param("email") String userEmail);

    //JPQL사용
    @Query("select a from Account a " +
            "where a.accountNum = :accountNum")
    Account findByAccountNum(@Param("accountNum") String accountNum);


    //JPQL사용
    @Query("select a from Account a " +
            "where a.member.email = :email" +
            " and a.accountNum = :accountNum")
    Account findByEmailAndAccountNum(@Param("email") String userEmail,@Param("accountNum") String accountNum);


}
