package com.bank.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account_detail")
@Getter
@Setter
@ToString
public class AccountDetail extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "email")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "account_num")
    private Account account;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "sender")
    private Member sender;


    @Column( nullable = true)
    private String comments;

    @Column(nullable = true)
    private int balance;

    @Column(nullable = true)
    private int total;

    private LocalDateTime inputTime;

}
