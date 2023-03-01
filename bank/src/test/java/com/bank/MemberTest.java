package com.bank;

import com.bank.dto.Memberdto;
import com.bank.entity.Member;
import com.bank.service.MemberService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;

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
        String email = "test7@gmail.com";
        String password = "12347";
        String name = "홍길동7";

        memberdto.setEmail(email);
        memberdto.setName(name);
        memberdto.setPassword(password);

        Member saveMember =  memberService.joinMember(memberdto,passwordEncoder);

        assertEquals(email , saveMember.getEmail());
        assertEquals(name , saveMember.getName());

//        for(int i = 0; i < 10; i++){
//            String email = "test" + i + "@gmail.com";
//            String password = "1234" + i;
//            String name = "홍길동" + i;
//            memberdto.setEmail(email);
//            memberdto.setName(name);
//            memberdto.setPassword(password);
//
//            Member saveMember =  memberService.joinMember(memberdto,passwordEncoder);
//
//            assertEquals(email , saveMember.getEmail());
//            assertEquals(name , saveMember.getName());
//        }

    }
}


