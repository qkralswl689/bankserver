package com.bank.dto;

import com.bank.entity.Account;
import com.bank.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AccountDetaildto {


    private String memberEmail;

    private String senderEmail;

    private String fromAccountNum;

    private String toAccountNum;

    private Member sender;

    private Account fromAccout;

    private Member member;

    private Account toAccount;

    private String comments;

    private int balance;

    private int total;

    private LocalDateTime inputTime;

    private LocalDateTime outTime;
}
