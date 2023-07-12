package com.ddukddak.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.java.Log;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;
    
    private boolean banned;
    
    @Column(name = "login")
    private String intraId;
    
    private boolean master;
    
    private Long reportNumber;

    private LocalDateTime reportTime;

    @ManyToOne(fetch = FetchType.LAZY)
    private PublicChatRoom publicChatRoom;

    @OneToMany(mappedBy = "user")
    private List<ChatTable> chatTables = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();
}
