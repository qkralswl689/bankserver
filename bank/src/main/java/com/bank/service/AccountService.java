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
import org.jetbrains.annotations.NotNull;
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

    @Autowired
    private final  FriendService friendService;

    @Autowired final Mocking mocking;

    // 계좌생성
    @Transactional
    public Account createAccount(Account account ){

        String userEmail = account.getMember().getEmail();
        String accountNum = account.getAccountNum();

        if(chkUserAndAccount(userEmail,accountNum) == true){

            Member member = memberRepository.findByEmail(userEmail);

            account.setMember(member);
            account.setAccountNum(accountNum);

        }


        return accountRepository.save(account);
    }

    // 전체 계좌조회
    @Transactional
    public List<Account> searchAccount(String userEmail ){

        List<Account> accountList = accountRepository.findByEmail(userEmail);

        return accountList;
    }

    // 특정 계좌 상세 전체 조회
    @Transactional
    public List<AccountDetail> searchAccountByNum(String userEmail,String accountNum ){

        List<AccountDetail> accountDetail = accountDetailRepository.findByEmailAndAccount(userEmail,accountNum);

        return accountDetail;
    }


    // 계좌이체
    @Transactional
    public AccountDetail transferAccount(@NotNull AccountDetaildto accountdto) throws InterruptedException {


        Member sender = memberRepository.findByEmail(accountdto.getSenderEmail());
        Account fromAccount = accountRepository.findByEmailAndAccountNum(sender.getEmail(),accountdto.getFromAccountNum());

        Member member = memberRepository.findByEmail(accountdto.getMemberEmail());
        Account toAccount = accountRepository.findByEmailAndAccountNum(member.getEmail(),accountdto.getToAccountNum());

        Friend friend =  friendService.checkFriend(member.getEmail(),sender.getEmail());

        if( friend == null){
            throw new IllegalStateException("해당 회원의 친구가 아닙니다.");
        }

        if(chkUserAndAccount(sender.getEmail(),fromAccount.getAccountNum()) == true && chkUserAndAccount(member.getEmail(),toAccount.getAccountNum()) == true){


            if(fromAccount.getTotal() < accountdto.getBalance()){
                throw new IllegalStateException("계좌 잔액이 부족합니다.");
            }

            AccountDetail fromAcDetail = new AccountDetail();
            AccountDetail toAcDetail = new AccountDetail();

            fromAcDetail.setMember(member);
            fromAcDetail.setSender(sender);
            fromAcDetail.setAccount(fromAccount);
            fromAcDetail.setBalance(accountdto.getBalance());
            fromAcDetail.setComments(accountdto.getComments());
            fromAcDetail.setTotal(fromAccount.getTotal() - accountdto.getBalance());
            fromAcDetail.setRegTime(LocalDateTime.now());
            fromAcDetail.setUpdateTime(LocalDateTime.now());
            fromAcDetail.setInputTime(LocalDateTime.now());

            fromAccount.setTotal(fromAccount.getTotal() - accountdto.getBalance());
            accountRepository.save(fromAccount);

            AccountDetail saveFromAc = accountDetailRepository.save(fromAcDetail);

            toAcDetail.setMember(sender);
            toAcDetail.setSender(member);
            toAcDetail.setAccount(toAccount);
            toAcDetail.setBalance(accountdto.getBalance());
            toAcDetail.setComments(accountdto.getComments());
            toAcDetail.setTotal(toAccount.getTotal() + accountdto.getBalance());
            toAcDetail.setRegTime(LocalDateTime.now());
            toAcDetail.setUpdateTime(LocalDateTime.now());
            toAcDetail.setInputTime(LocalDateTime.now());

            toAccount.setTotal(toAccount.getTotal() + accountdto.getBalance());
            accountRepository.save(toAccount);

            AccountDetail saveToAc = accountDetailRepository.save(toAcDetail);

            if(saveFromAc != null && saveToAc != null){
                // 알람
                mocking.alarm();
            }else {
                throw new IllegalStateException("이체 실패.");
            }

            return saveToAc;

        }

        return null;
    }


    // 사용자,계좌확인
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
