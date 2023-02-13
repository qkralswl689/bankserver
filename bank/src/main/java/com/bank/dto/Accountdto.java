package com.bank.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Getter
@Setter
public class Accountdto {


    private String email;

    private String name;

    private String sender;

    private String accountNum;

    private String comments;

    private int balance;

    private int total;

    private LocalDateTime inputTime;
}