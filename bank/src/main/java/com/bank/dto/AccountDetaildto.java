package com.bank.dto;

import com.bank.entity.Account;
import com.bank.entity.Member;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
public class AccountDetaildto {


    private Member member;

    private Member sender;

    private Account account;

    private String comments;

    private int balance;

    private int total;

    private LocalDateTime inputTime;
}
