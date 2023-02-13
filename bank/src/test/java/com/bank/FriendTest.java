package com.bank;

import com.bank.dto.Frienddto;
import com.bank.dto.Memberdto;
import com.bank.entity.Friend;
import com.bank.service.FriendService;
import com.bank.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;

@SpringBootTest
public class FriendTest {


    @Autowired
    private FriendService friendService;

    @Test
    @DisplayName("친구추가")
    public void addFriend() throws Exception{

        Frienddto frienddto = new Frienddto();

        String userEmail = "test4@gmail.com";
        String friendEmail = "test9@gmail.com";

        frienddto.setUserEmail(userEmail);
        frienddto.setFriendEmail(friendEmail);

        friendService.addFriend(frienddto);

    }

    @Test
    @DisplayName("친구삭제")
    public void deleteFriend() throws Exception{

        String userEmail = "test0@gmail.com";
        String friendEmail = "test3@gmail.com";

        friendService.deleteFriend(userEmail,friendEmail);

    }

    @Test
    @DisplayName("친구목록 조회")
    public void searchFriends() throws Exception{

        String userEmail = "test0@gmail.com";

        List<Friend> myfriends = friendService.searchFriend(userEmail);

        for (int i = 0; i < myfriends.size(); i++){

            System.out.println(myfriends.get(i).getFriendEmail() + " Friend " + i);
        }

    }
}


