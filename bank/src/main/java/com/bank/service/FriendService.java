package com.bank.service;

import com.bank.dto.Frienddto;
import com.bank.dto.Memberdto;
import com.bank.entity.Friend;
import com.bank.entity.Member;
import com.bank.repository.FriendRepository;
import com.bank.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

        friend.setUserEmail(frienddto.getUserEmail());
        friend.setFriendEmail(frienddto.getFriendEmail());
        friend.setRegTime(LocalDateTime.now());
        friend.setUpdateTime(LocalDateTime.now());

        validateDuplicateMember(friend);

       return friendRepository.save(friend);
    }



    public void validateDuplicateMember(Friend friend){

       // List friends = friendRepository.findByFriends(friend.getUserEmail());
        List<Friend> friends = friendRepository.findByFriends(friend.getUserEmail());

        Map<String, String> map = new HashMap<>();
        for (Friend friendlist : friends) {
            map.put(friendlist.getUserEmail(), friendlist.getFriendEmail());
        }

        for (int i = 0; i < friends.size(); i++){
            if(map.get(0).equals(friend.getFriendEmail())){
                throw new IllegalStateException("이미 추가된 친구입니다");
            }
        }
    }
}
