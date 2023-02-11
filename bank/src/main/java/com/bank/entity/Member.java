package com.bank.entity;

import com.bank.dto.Memberdto;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.persistence.*;

@Entity
@Table(name = "member")
@Getter
@Setter
@ToString
public class Member extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String email;

    private String password;

    public static Member createMember(Memberdto memberFormDto, PasswordEncoder passwordEncoder){

        Member member = new Member();
        member.setEmail(memberFormDto.getEmail());

        String password = passwordEncoder.encode(memberFormDto.getPassword());
        member.setPassword(password);
        return member;
    }

}
