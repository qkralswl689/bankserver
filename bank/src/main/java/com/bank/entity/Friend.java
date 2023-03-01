package com.bank.entity;

import com.bank.dto.Memberdto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "friend")
@Getter
@Setter
@ToString
public class Friend extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "member_id")
    private Member member;


    @ManyToOne(fetch = FetchType.EAGER )
    @JoinColumn(name = "friend_id")
    private Member friend;




}
