package com.bank.repository;

import com.bank.entity.AccountDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AccountDetailRepository extends JpaRepository<AccountDetail, Long> {

//    //JPQL사용
//    @Query("select a from AccountDetail a " +
//            "where a.member.email = :email " +
//            "order by a.inputTime ")
//    List<AccountDetail> findByEmailAndAccount(@Param("email") String userEmail);

    //JPQL사용
    @Query("select a from AccountDetail a " +
            "where a.member.email = :email " +
            "and a.account.accountNum = :accountNum " +
            "order by a.inputTime ")
    List<AccountDetail> findByEmailAndAccount(@Param("email") String userEmail, @Param("accountNum") String accountNum);


}
