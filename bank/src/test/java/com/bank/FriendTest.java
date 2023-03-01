package com.bank;

import com.bank.entity.Friend;
import com.bank.entity.Member;
import com.bank.repository.FriendRepository;
import com.bank.repository.MemberRepository;
import com.bank.service.FriendService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class FriendTest {


    @Autowired
    private FriendService friendService;

    @Autowired
    private FriendRepository friendRepository;

    @Autowired
    private MemberRepository memberRepository;



    @Test
    @DisplayName("친구추가")
    public void addFriend() throws Exception{

        String email = "test0@gmail.com";
        String friendEmail = "test5@gmail.com";

        friendService.addFriend(email,friendEmail);

        Friend saveFriend = friendRepository.findByEmail(email,friendEmail);
        Friend saveFriend2 = friendRepository.findByEmail(friendEmail,email);

        assertEquals(saveFriend.getMember().getEmail(), saveFriend2.getFriend().getEmail());
        assertEquals(saveFriend2.getMember().getEmail(), saveFriend.getFriend().getEmail());
    }
// 시간 나면 다시
//    @Test
//    @DisplayName("친구삭제")
//    public void deleteFriend() throws Exception{
//
//        String email = "test1@gmail.com";
//        String friendEmail = "test5@gmail.com";
//
//        friendService.deleteFriend(email,friendEmail);
//
//    }

    @Test
    @DisplayName("친구목록 조회")
    public void searchFriends() throws Exception{

        String userEmail = "test0@gmail.com";

        List<Friend> myfriends = friendService.searchFriend(userEmail);

        for (int i = 0; i < myfriends.size(); i++){

            System.out.println(" Friend " + i + ": " + myfriends.get(i).getFriend().getEmail() );
        }

    }
}


