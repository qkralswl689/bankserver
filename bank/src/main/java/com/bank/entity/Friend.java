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

    @Column(unique = true,nullable = false)
    private String userEmail;

    @Column(nullable = false)
    private String friendEmail;




}
