package com.bank.repository;

import com.bank.entity.Friend;
import com.bank.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface FriendRepository extends JpaRepository<Friend, Long> , QuerydslPredicateExecutor<Friend> {

    //JPQL사용
    @Query("select f from Friend f " +
            "where f.member.email = :email")
    List<Friend> findByFriends(@Param("email") String email);

    //JPQL사용
    @Query("select f from Friend f " +
            "where f.member.email = :email " +
            "and f.friend.email = :friendEmail")
    Friend findByEmail(@Param("email") String email,@Param("friendEmail") String friendEmail);

}
