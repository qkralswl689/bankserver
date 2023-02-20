package com.bank.service;

import com.bank.dto.Accountdto;
import com.bank.entity.Account;
import com.bank.entity.Friend;
import com.bank.entity.Member;
import com.bank.repository.AccountRepository;
import com.bank.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
@Component
public class AccountService {


    @Autowired
    private final AccountRepository accountRepository;

    @Autowired
    private final MemberRepository memberRepository;

    private final  FriendService friendService;

    @Transactional
    public Account createAccount(Accountdto accountdto ){

        Account account = new Account();

        String userEmail = accountdto.getMember().getEmail();
        String accountNum = accountdto.getAccountNum();

        if(chkUserAndAccount(userEmail,accountNum) == true){

            Member member = memberRepository.findByEmail(userEmail);

            account.setMember(member);
            account.setAccountNum(accountNum);
            account.setBalance(accountdto.getBalance());
            account.setTotal(accountdto.getTotal());
            account.setRegTime(LocalDateTime.now());
            account.setUpdateTime(LocalDateTime.now());
        }


        return accountRepository.save(account);
    }

    @Transactional
    public List<Account> searchAccount(String userEmail ){

        List<Account> accountList = accountRepository.findByEmail(userEmail);

        return accountList;
    }

    @Transactional
    public Account transferAccount(Accountdto accountdto){

        Account account = new Account();
        String email = accountdto.getMember().getEmail();
        String senderEmail = accountdto.getSender().getEmail();
        String accountNum = accountdto.getAccountNum();

        Friend friend =  friendService.checkFriend(email,senderEmail);

        if( friend == null){
            throw new IllegalStateException("해당 회원의 친구가 아닙니다.");
        }

        if(chkUserAndAccount(email,accountNum) == true){


            account = accountRepository.findByAccountNum(accountdto.getAccountNum());

            Member sender = memberRepository.findByEmail(accountdto.getSender().getEmail());

            account.setSender(sender);
            account.setBalance(accountdto.getBalance());
            account.setComments(accountdto.getComments());
            account.setTotal(account.getTotal() + accountdto.getBalance());
            account.setRegTime(LocalDateTime.now());
            account.setUpdateTime(LocalDateTime.now());
        }
        return accountRepository.save(account);
    }


    public boolean chkUserAndAccount(String userEmail , String accountNum){

        Member member = memberRepository.findByEmail(userEmail);

        if(member == null){
            return false;
        }else{
            List<Account> account = accountRepository.findByEmail(userEmail);

            if(account != null){

                for( int i = 0; i < account.size(); i++){

                    if(account.get(i).getAccountNum().equals(accountNum)){
                        return false;
                    }
                }
            }

            return true;

        }
    }

}
