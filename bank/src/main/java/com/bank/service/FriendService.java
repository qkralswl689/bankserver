package com.bank.service;

import com.bank.dto.Frienddto;
import com.bank.entity.Friend;
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
public class FriendService  {


    @Autowired
    private final FriendRepository friendRepository;


    @Transactional
    public Friend addFriend(Frienddto frienddto ){

        Friend friend = new Friend();

        String userEmail = frienddto.getUserEmail();
        String friendEmail = frienddto.getFriendEmail();

        Friend friendInfo = checkFriend(userEmail,friendEmail);

        if( friendInfo != null){
            throw new IllegalStateException("이미 추가된 친구입니다");
        }else{
            friend.setUserEmail(userEmail);
            friend.setFriendEmail(friendEmail);
            friend.setRegTime(LocalDateTime.now());
            friend.setUpdateTime(LocalDateTime.now());
        }

       return friendRepository.save(friend);
    }

    @Transactional
    public void deleteFriend(String userEmail , String frinedEmail){

        Friend friend = checkFriend(userEmail,frinedEmail);

        if( friend == null){
            throw new IllegalStateException("해당 회원의 친구가 아닙니다.");
        }

        friendRepository.deleteById(friend.getId());
    }


    @Transactional
    public List<Friend> searchFriend(String userEmail ){

        List<Friend> friends = friendRepository.findByFriends(userEmail);

        return friends;
    }

    public Friend checkFriend(String userEmail , String frinedEmail){

        List<Friend> friends = friendRepository.findByFriends(userEmail);

        for( int i = 0; i < friends.size(); i++){

            if(friends.get(i).getFriendEmail().equals(frinedEmail)){
                return friends.get(i);
            }
        }

        return null;
    }


}
