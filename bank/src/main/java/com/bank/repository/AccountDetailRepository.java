package com.bank.repository;

import com.bank.entity.Account;
import com.bank.entity.AccountDetail;
import com.bank.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail, Long> {
//
//    //JPQL사용
//    @Query("select a from Account a " +
//            "where a.member.email = :email")
//    List<Account> findByEmail(@Param("email") String userEmail);
//
//    //JPQL사용
//    @Query("select a from Account a " +
//            "where a.accountNum = :accountNum")
//    Account findByAccountNum(@Param("accountNum") String accountNum);
//
//
//    //JPQL사용
//    @Query("select a from Account a " +
//            "where a.member.email = :email" +
//            " and a.accountNum = :accountNum")
//    Account findByEmailAndAccountNum(@Param("email") String userEmail,@Param("accountNum") String accountNum);
//
//    //JPQL사용
//    @Query("select a from Account a " +
//            "where a.member = :member" )
//    Account findByMember(@Param("member")Member member);
}