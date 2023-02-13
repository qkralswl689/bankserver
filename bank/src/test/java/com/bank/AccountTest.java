package com.bank;

import com.bank.dto.Frienddto;
import com.bank.entity.Friend;
import com.bank.service.AccountService;
import com.bank.service.FriendService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AccountTest {


    @Autowired
    private AccountService accountService;

    @Test
    @DisplayName("계좌생성")
    public void createAccount() throws Exception{



    }

//    @Test
//    @DisplayName("친구목록 조회")
//    public void searchFriends() throws Exception{
//
//        String userEmail = "test0@gmail.com";
//
//        List<Friend> myfriends = friendService.searchFriend(userEmail);
//
//        for (int i = 0; i < myfriends.size(); i++){
//
//            System.out.println(myfriends.get(i).getFriendEmail() + " Friend " + i);
//        }
//
//    }
}


