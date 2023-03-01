package com.bank.dto;

import com.bank.entity.Member;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Accountdto {


    private String email;

    private String accountNum;

    private int total;
}
