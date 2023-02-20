package com.bank;

import com.bank.dto.Accountdto;
import com.bank.entity.Account;
import com.bank.entity.Member;
import com.bank.repository.AccountRepository;
import com.bank.repository.MemberRepository;
import com.bank.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class AccountTest {


    @Autowired
    private AccountService accountService;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private MemberRepository memberRepository;

    @Test
    @DisplayName("계좌생성")
    public void createAccount() throws Exception{

        Accountdto accountdto = new Accountdto();

        Member member = memberRepository.findByEmail("test0@gmail.com");
        accountdto.setMember(member);
        accountdto.setAccountNum("1234-5678-9990");

        accountService.createAccount(accountdto);

        List<Account> account = accountRepository.findByEmail(accountdto.getMember().getEmail());

        for(int i = 0; i < account.size(); i++){
            System.out.println("Email : " + account.get(i).getMember().getEmail());
            System.out.println("AccountNum : " + account.get(i).getAccountNum());
            System.out.println("name : " + account.get(i).getMember().getName());

        }

    }

    @Test
    @DisplayName("계좌조회")
    public void searchAccount() throws Exception{

        String userEmail = "test0@gmail.com";

        List<Account> account =  accountService.searchAccount(userEmail);

        for(int i = 0; i < account.size(); i++){
            System.out.println("Email : " + account.get(i).getMember().getEmail());
            System.out.println("AccountNum : " + account.get(i).getAccountNum());
            System.out.println("name : " + account.get(i).getMember().getName());
            System.out.println("Balance : " + account.get(i).getBalance());
            System.out.println("Total : " + account.get(i).getTotal());

        }
    }

    @Test
    @DisplayName("계좌이체")
    public void transferAccount() throws Exception{

        Accountdto accountdto = new Accountdto();

        String memberEmail = "test0@gmail.com";
        String senderEmail = "test4@gmail.com";

        Member member = memberRepository.findByEmail(memberEmail);
        Member sender = memberRepository.findByEmail(senderEmail);

        accountdto.setMember(member);
        accountdto.setAccountNum("1234-5678-9990");
        accountdto.setSender(sender);
        accountdto.setBalance(1500);
        accountdto.setComments("input TEST");

        accountService.transferAccount(accountdto);

        List<Account> accountCheck =  accountService.searchAccount(memberEmail);

        for(int i = 0; i < accountCheck.size(); i++){
            System.out.println("Email : " + accountCheck.get(i).getMember().getEmail());
            System.out.println("AccountNum : " + accountCheck.get(i).getAccountNum());
            System.out.println("name : " + accountCheck.get(i).getMember().getName());
            System.out.println("Balance : " + accountCheck.get(i).getBalance());
            System.out.println("Total : " + accountCheck.get(i).getTotal());

        }
    }
}


