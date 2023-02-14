package com.bank.service;

import com.bank.entity.Friend;
import com.bank.entity.Member;
import com.bank.repository.FriendRepository;
import com.bank.repository.MemberRepository;
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

    @Autowired
    private final MemberRepository memberRepository;


    @Transactional
    public void addFriend( String email, String friendEmail ){


        Member member = memberRepository.findByEmail(email);
        Member member2 = memberRepository.findByEmail(friendEmail);

        if(member == null || member2 == null){
            throw new IllegalStateException("회원이 존재하지 않습니다.");
        }

        Friend friendInfo = checkFriend(email,friendEmail);

        if( friendInfo != null){

            throw new IllegalStateException("이미 추가된 친구입니다");

        }

        Friend friend = new Friend();
        friend.setMember(member);
        friend.setFriend(member2);
        friend.setRegTime(LocalDateTime.now());
        friend.setUpdateTime(LocalDateTime.now());

        friendRepository.save(friend);

        Friend friend2 = new Friend();
        friend2.setMember(member2);
        friend2.setFriend(member);
        friend2.setRegTime(LocalDateTime.now());
        friend2.setUpdateTime(LocalDateTime.now());

        friendRepository.save(friend2);


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

            if(friends.get(i).getFriend().getEmail().equals(frinedEmail)){
                return friends.get(i);
            }
        }

        return null;
    }


}
