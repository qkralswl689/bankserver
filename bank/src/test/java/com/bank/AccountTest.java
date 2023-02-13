package com.bank;

import com.bank.dto.Accountdto;
import com.bank.dto.Frienddto;
import com.bank.entity.Account;
import com.bank.entity.Friend;
import com.bank.repository.AccountRepository;
import com.bank.service.AccountService;
import com.bank.service.FriendService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.List;

@SpringBootTest
public class AccountTest {


    @Autowired
    private AccountService accountService;

    @Autowired

    private AccountRepository accountRepository;

    @Test
    @DisplayName("계좌생성")
    public void createAccount() throws Exception{

        Accountdto accountdto = new Accountdto();

        accountdto.setEmail("test0@gmail.com");
        accountdto.setAccountNum("1234-5678-9999");
        accountdto.setName("홍길동0");

        accountService.createAccount(accountdto);

        List<Account> account = accountRepository.findByAccount(accountdto.getEmail());

        for(int i = 0; i < account.size(); i++){
            System.out.println("Email : " + account.get(i).getEmail());
            System.out.println("AccountNum : " + account.get(i).getAccountNum());
            System.out.println("name : " + account.get(i).getName());

        }

    }


}


