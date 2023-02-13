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

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String sender;

    @Column(unique = true, nullable = false)
    private String accountNum;

    @Column( nullable = true)
    private String comments;

    @Column(nullable = false)
    private int balance;

    @Column(nullable = false)
    private int total;

    private LocalDateTime inputTime;

}
