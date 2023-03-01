package com.bank;

import com.bank.dto.AccountDetaildto;
import com.bank.entity.Account;
import com.bank.entity.AccountDetail;
import com.bank.entity.Member;
import com.bank.repository.AccountRepository;
import com.bank.repository.MemberRepository;
import com.bank.service.AccountService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

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


        Account account = new Account();

        Member member = memberRepository.findByEmail("test0@gmail.com");
        account.setMember(member);
        account.setAccountNum("1234-5678-9991");
        account.setTotal(500000);

        accountService.createAccount(account);

        Account saveAccount = accountRepository.findByEmailAndAccountNum(member.getEmail(),account.getAccountNum());

        assertEquals(account.getMember().getEmail(), saveAccount.getMember().getEmail());
        assertEquals(account.getAccountNum(), saveAccount.getAccountNum());
        assertEquals(account.getMember().getName(), saveAccount.getMember().getName());

    }

    @Test
    @DisplayName("전체 계좌 정보 조회")
    public void searchAccount() throws Exception{

        String userEmail = "test0@gmail.com";

        List<Account> account =  accountService.searchAccount(userEmail);

        for(int i = 0; i < account.size(); i++){
            System.out.println("Email : " + account.get(i).getMember().getEmail());
            System.out.println("AccountNum : " + account.get(i).getAccountNum());
            System.out.println("name : " + account.get(i).getMember().getName());
            System.out.println("Total : " + account.get(i).getTotal());
        }
    }

    @Test
    @DisplayName("특정 계좌 상세 전체조회")
    public void searchAccountByNum() throws Exception{

        String userEmail = "test0@gmail.com";
        String accountNum = "1234-5678-9990";

        List<AccountDetail> accountDetail =  accountService.searchAccountByNum(userEmail,accountNum);

        for(int i = 0; i < accountDetail.size(); i++){
            System.out.println("Email : " + accountDetail.get(i).getMember().getEmail());
            System.out.println("AccountNum : " + accountDetail.get(i).getAccount().getAccountNum());
            System.out.println("Name : " + accountDetail.get(i).getMember().getName());
            System.out.println("Sender : " + accountDetail.get(i).getSender().getEmail());
            System.out.println("balance : " + accountDetail.get(i).getBalance());
            System.out.println("Comments : " + accountDetail.get(i).getComments());
            System.out.println("inputTime : " + accountDetail.get(i).getInputTime());
            System.out.println("Total : " + accountDetail.get(i).getTotal());
        }

    }

    @Test
    @DisplayName("계좌이체")
    public void transferAccount() throws Exception{

        AccountDetaildto accountdto = new AccountDetaildto();

        String senderEmail = "test0@gmail.com";
        String fromAccountNum = "1234-5678-9991";

        String memberEmail = "test5@gmail.com";
        String toAccountNum = "1234-5678-9995";

//        String senderEmail = "test5@gmail.com";
//        String fromAccountNum = "1234-5678-9995";
//
//        String memberEmail = "test0@gmail.com";
//        String toAccountNum = "1234-5678-9991";

        accountdto.setSenderEmail(senderEmail);
        accountdto.setFromAccountNum(fromAccountNum);
        accountdto.setMemberEmail(memberEmail);
        accountdto.setToAccountNum(toAccountNum);
        accountdto.setBalance(5000);
        accountdto.setComments("input TEST2");

        AccountDetail transferAc =  accountService.transferAccount(accountdto);

        if(transferAc != null){
            System.out.println("Email : " + transferAc.getMember().getEmail());
            System.out.println("AccountNum : " + transferAc.getAccount().getAccountNum());
            System.out.println("Name : " + transferAc.getMember().getName());
            System.out.println("Sender : " + transferAc.getSender().getEmail());
            System.out.println("Balance : " + transferAc.getBalance());
            System.out.println("Comments : " + transferAc.getComments());
            System.out.println("InputTime : " + transferAc.getInputTime());
            System.out.println("Total : " + transferAc.getTotal());
        }else{
            System.out.println("실패");
       }

    }
}


