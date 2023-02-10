package com.bank;

import com.bank.dto.Memberdto;
import com.bank.entity.Member;
import com.bank.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
public class MemberTest{

    @Autowired
    private MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    @DisplayName("회원가입")
    public Member joinMember(String email, String password) throws Exception{

        Memberdto memberdto = new Memberdto();

        memberdto.setEmail(email);
        memberdto.setPassword(password);

        Member member = memberService.joinMember(memberdto,passwordEncoder);

        return member;
    }
}


