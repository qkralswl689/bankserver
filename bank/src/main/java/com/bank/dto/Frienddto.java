package com.bank.dto;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Getter
@Setter
public class Frienddto {


    private String userEmail;

    private String friendEmail;
}
