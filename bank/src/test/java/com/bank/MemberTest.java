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
public class MemberTest {


    @Autowired
    private MemberService memberService;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Test
    @DisplayName("회원가입")
    public void joinMember() throws Exception{

        Memberdto memberdto = new Memberdto();
        
        for(int i = 0; i < 5; i++){
            String email = "test" + i + "@gmail.com";
            String password = "1234" + i;
            memberdto.setEmail(email);
            memberdto.setPassword(password);

            memberService.joinMember(memberdto,passwordEncoder);

        }

    }
}


