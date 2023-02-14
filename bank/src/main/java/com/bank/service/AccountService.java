package com.bank.service;

import com.bank.dto.Accountdto;
import com.bank.entity.Account;
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

    @Transactional
    public Account createAccount(Accountdto accountdto ){

        Account account = new Account();

        String userEmail = accountdto.getEmail();
        String accountNum = accountdto.getAccountNum();

        if(chkUserAndAccount(userEmail,accountNum) == true){

            Member member = memberRepository.findByEmail(userEmail);

            account.setMember(member);
            account.setAccountNum(accountNum);
            account.setName(accountdto.getName());
            account.setBalance(accountdto.getBalance());
            account.setTotal(accountdto.getTotal());
            account.setRegTime(LocalDateTime.now());
            account.setUpdateTime(LocalDateTime.now());
        }


        return accountRepository.save(account);
    }

    @Transactional
    public List<Account> searchAccount(String userEmail ){

        List<Account> accountList = accountRepository.findByAccount(userEmail);

        return accountList;
    }


    public boolean chkUserAndAccount(String userEmail , String accountNum){

        Member member = memberRepository.findByEmail(userEmail);

        if(member == null){
            return false;
        }else{
            List<Account> account = accountRepository.findByAccount(userEmail);

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
