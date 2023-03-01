package com.bank;

import com.bank.dto.AccountDetaildto;
import com.bank.dto.Accountdto;
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
import org.springframework.security.core.parameters.P;

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

        Accountdto accountdto = new Accountdto();

        accountdto.setEmail("test8@gmail.com");
        accountdto.setAccountNum("1234-5678-9998");
        accountdto.setTotal(100000);

        Account saveAccount = accountService.createAccount(accountdto);


        assertEquals(accountdto.getEmail(), saveAccount.getMember().getEmail());
        assertEquals(accountdto.getAccountNum(), saveAccount.getAccountNum());

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

        if(accountDetail != null) {
            for(int i = 0; i < accountDetail.size(); i++){

                System.out.println("Email : " + accountDetail.get(i).getMember().getEmail());
                System.out.println("AccountNum : " + accountDetail.get(i).getAccount().getAccountNum());
                System.out.println("Name : " + accountDetail.get(i).getMember().getName());
                System.out.println("Sender : " + accountDetail.get(i).getSender().getEmail());
                System.out.println("balance : " + accountDetail.get(i).getBalance());
                System.out.println("Comments : " + accountDetail.get(i).getComments());
                System.out.println("inputTime : " + accountDetail.get(i).getInputTime());
                System.out.println("outTime : " + accountDetail.get(i).getOutTime());
                System.out.println("Total : " + accountDetail.get(i).getTotal());
            }
        }else{
            throw new IllegalStateException("사용자와 계좌가 일치하지 않습니다.");
        }


    }

    @Test
    @DisplayName("계좌이체")
    public void transferAccount() throws Exception{

        AccountDetaildto accountdto = new AccountDetaildto();

        String senderEmail = "test0@gmail.com";
        String fromAccountNum = "1234-5678-9990";

//        String senderEmail = "test0@gmail.com";
//        String fromAccountNum = "1234-5678-9991";

        String memberEmail = "test7@gmail.com";
        String toAccountNum = "1234-5678-9997";

//        String senderEmail = "test5@gmail.com";
//        String fromAccountNum = "1234-5678-9995";
//
//        String memberEmail = "test0@gmail.com";
//        String toAccountNum = "1234-5678-9991";

        accountdto.setSenderEmail(senderEmail);
        accountdto.setFromAccountNum(fromAccountNum);
        accountdto.setMemberEmail(memberEmail);
        accountdto.setToAccountNum(toAccountNum);
        accountdto.setBalance(100000);
        accountdto.setComments("input TEST4");

        AccountDetail transferAc =  accountService.transferAccount(accountdto);

        if(transferAc != null){
            System.out.println("Email : " + transferAc.getMember().getEmail());
            System.out.println("AccountNum : " + transferAc.getAccount().getAccountNum());
            System.out.println("Name : " + transferAc.getMember().getName());
            System.out.println("Sender : " + transferAc.getSender().getEmail());
            System.out.println("Balance : " + transferAc.getBalance());
            System.out.println("Comments : " + transferAc.getComments());
            System.out.println("InputTime : " + transferAc.getInputTime());
            System.out.println("outTime : " + transferAc.getOutTime());
            System.out.println("Total : " + transferAc.getTotal());
        }else{
            System.out.println("실패");
       }

    }
}


