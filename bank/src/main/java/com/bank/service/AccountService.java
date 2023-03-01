package com.bank.service;

import com.bank.dto.AccountDetaildto;
import com.bank.dto.Accountdto;
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
    public Account createAccount(Accountdto accountdto ){

        String userEmail = accountdto.getEmail();
        String accountNum = accountdto.getAccountNum();

        Account account = new Account();

        if(chkUserAndAccount(userEmail,accountNum) == false){

            Member member = memberRepository.findByEmail(userEmail.trim());

            account.setMember(member);
            account.setAccountNum(accountNum);
            account.setTotal(accountdto.getTotal());

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

        if(chkUserAndAccount(userEmail,accountNum) == true){

            List<AccountDetail> accountDetail = accountDetailRepository.findByEmailAndAccount(userEmail,accountNum);
            return accountDetail;
        }
        return null;
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

            AccountDetail toAcdetail = new AccountDetail();
            AccountDetail fromAcDetail = new AccountDetail();

            toAcdetail.setMember(member);
            toAcdetail.setSender(sender);
            toAcdetail.setAccount(toAccount);
            toAcdetail.setBalance(accountdto.getBalance());
            toAcdetail.setComments(accountdto.getComments());
            toAcdetail.setTotal(toAccount.getTotal() + accountdto.getBalance());
            toAcdetail.setRegTime(LocalDateTime.now());
            toAcdetail.setUpdateTime(LocalDateTime.now());
            toAcdetail.setInputTime(LocalDateTime.now());

            AccountDetail saveToAcc = accountDetailRepository.save(toAcdetail);


            fromAcDetail.setMember(sender);
            fromAcDetail.setSender(member);
            fromAcDetail.setAccount(fromAccount);
            fromAcDetail.setBalance(accountdto.getBalance());
            fromAcDetail.setComments(accountdto.getComments());
            fromAcDetail.setTotal(fromAccount.getTotal() - accountdto.getBalance());
            fromAcDetail.setRegTime(LocalDateTime.now());
            fromAcDetail.setUpdateTime(LocalDateTime.now());
            fromAcDetail.setOutTime(LocalDateTime.now());

            fromAccount.setTotal(fromAccount.getTotal() - accountdto.getBalance());
            accountRepository.save(fromAccount);

            toAccount.setTotal(toAccount.getTotal() + accountdto.getBalance());
            accountRepository.save(toAccount);

            AccountDetail saveFromAcc = accountDetailRepository.save(fromAcDetail);

            if(saveToAcc != null && saveFromAcc != null){
                // 알람
                mocking.alarm();
            }else {
                throw new IllegalStateException("이체 실패.");
            }

            return saveFromAcc;

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
