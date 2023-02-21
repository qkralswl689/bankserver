package com.bank.service;

import com.bank.dto.AccountDetaildto;
import com.bank.entity.Account;
import com.bank.entity.AccountDetail;
import com.bank.entity.Friend;
import com.bank.entity.Member;
import com.bank.repository.AccountDetailRepository;
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
    private final AccountDetailRepository accountDetailRepository;

    @Autowired
    private final MemberRepository memberRepository;

    private final  FriendService friendService;

    @Transactional
    public Account createAccount(Account account ){

        //Account account = new Account();

        String userEmail = account.getMember().getEmail();
        String accountNum = account.getAccountNum();

        if(chkUserAndAccount(userEmail,accountNum) == true){

            Member member = memberRepository.findByEmail(userEmail);

            account.setMember(member);
            account.setAccountNum(accountNum);

        }


        return accountRepository.save(account);
    }

    @Transactional
    public List<Account> searchAccount(String userEmail ){

        List<Account> accountList = accountRepository.findByEmail(userEmail);

        return accountList;
    }

    @Transactional
    public AccountDetail transferAccount(AccountDetaildto accountdto){

        Account account = new Account();
        String email = accountdto.getAccount().getMember().getEmail();
        String senderEmail = accountdto.getSender().getEmail();
        String accountNum = accountdto.getAccount().getAccountNum();

        Friend friend =  friendService.checkFriend(email,senderEmail);

        if( friend == null){
            throw new IllegalStateException("해당 회원의 친구가 아닙니다.");
        }

        if(chkUserAndAccount(email,accountNum) == true){


            account = accountRepository.findByAccountNum(accountNum);

            AccountDetail accountDetail = new AccountDetail();

            Member member = memberRepository.findByEmail(accountdto.getMember().getEmail());
            Member sender = memberRepository.findByEmail(accountdto.getSender().getEmail());

            accountDetail.setMember(member);
            accountDetail.setSender(sender);
            accountDetail.setAccount(account);
            accountDetail.setBalance(accountdto.getBalance());
            accountDetail.setComments(accountdto.getComments());
            accountDetail.setTotal(account.getTotal() + accountdto.getBalance());
            accountDetail.setRegTime(LocalDateTime.now());
            accountDetail.setUpdateTime(LocalDateTime.now());
            accountDetail.setInputTime(LocalDateTime.now());

            account.setTotal(account.getTotal() + accountdto.getBalance());
            accountRepository.save(account);

            return accountDetailRepository.save(accountDetail);

        }

        return null;
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
                        return true;
                    }
                }
            }

            return false;

        }
    }

}
