package com.bank;

import com.bank.dto.Accountdto;
import com.bank.entity.Account;
import com.bank.repository.AccountRepository;
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
            System.out.println("Email : " + account.get(i).getMember().getEmail());
            System.out.println("AccountNum : " + account.get(i).getAccountNum());
            System.out.println("name : " + account.get(i).getName());

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
            System.out.println("name : " + account.get(i).getName());
            System.out.println("Balance : " + account.get(i).getBalance());
            System.out.println("Total : " + account.get(i).getTotal());

        }
    }

}


