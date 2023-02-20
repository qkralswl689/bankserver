package com.bank.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "account")
@Getter
@Setter
@ToString
public class Account extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "email")
    private Member member;

    @ManyToOne(fetch = FetchType.EAGER , cascade = CascadeType.ALL)
    @JoinColumn(name = "sender")
    private Member sender;

    @Column(unique = true, nullable = false)
    private String accountNum;

    @Column( nullable = true)
    private String comments;

    @Column(nullable = true)
    private int balance;

    @Column(nullable = true)
    private int total;

    private LocalDateTime inputTime;

}
