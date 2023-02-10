package com.bank.service;

import com.bank.dto.Memberdto;
import com.bank.entity.Member;
import com.bank.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;

@Service
@Transactional
@RequiredArgsConstructor
public class MemberService implements UserDetailsService {

    private final MemberRepository memberRepository;

    @Transactional
    public Member joinMember(@Valid Memberdto memberdto ,PasswordEncoder passwordEncoder){

        Member member = new Member();
        String password = passwordEncoder.encode(memberdto.getPassword());

        member.setEmail(memberdto.getEmail());
        member.setPassword(password);

        validateDuplicateMember(member);

       return memberRepository.save(member);
    }


    public void validateDuplicateMember(Member member){

        Member findMember = memberRepository.findByEmail(member.getEmail());

        if(findMember != null){
            throw new IllegalStateException("이미 가입된 회원입니다");
        }
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        Member member = memberRepository.findByEmail(email);

        if(member == null){
            throw new UsernameNotFoundException(email);
        }

        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .build();
    }
}
