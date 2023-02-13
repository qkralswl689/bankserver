package com.bank.service;

import com.bank.dto.Accountdto;
import com.bank.dto.Frienddto;
import com.bank.entity.Account;
import com.bank.entity.Friend;
import com.bank.repository.AccountRepository;
import com.bank.repository.FriendRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Component
public class AccountService {


    @Autowired
    private final AccountRepository accountRepository;

    @Transactional
    public Account createAccount(Accountdto accountdto ){

        Account account = new Account();

        String userEmail = accountdto.getEmail();
        String  accountNum = accountdto.getAccountNum();

        if(checkAccount(userEmail,accountNum) == true){

        }

//        if( friendInfo != null){
//            throw new IllegalStateException("이미 추가된 친구입니다");
//        }else{
//            friend.setUserEmail(userEmail);
//            friend.setFriendEmail(friendEmail);
//            friend.setRegTime(LocalDateTime.now());
//            friend.setUpdateTime(LocalDateTime.now());
//        }
//
//        return friendRepository.save(friend);
    }


    public boolean checkAccount(String userEmail , String accountNum){


        List<Account> acount = accountRepository.findByAccount(userEmail);

        for( int i = 0; i < acount.size(); i++){

            if(acount.get(i).getAccountNum().equals(accountNum)){
                return false;
            }
        }

        return true;
    }

}
