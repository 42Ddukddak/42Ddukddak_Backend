package com.ddukddak.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.AccessLevel;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Token {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    private Long id;

    @Column(name = "user_name")
    private String userName;
    private String accessToken;
    private String refreshToken;
    private String UUID;

    public Token(String name, String access, String refresh){
        this.UUID = java.util.UUID.randomUUID().toString();
        this.accessToken = access;
        this.refreshToken = refresh;
        this.userName = name;
    }
}
