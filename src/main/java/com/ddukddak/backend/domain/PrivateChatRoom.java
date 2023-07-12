package com.ddukddak.backend.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class PrivateChatRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "private_chat_room_id")
    private Long id;
    
    private String roomName;
    
    private LocalDateTime createTime;

    private Long restTime;

    @OneToMany(mappedBy = "privateChatRoom")
    private List<ChatTable> users = new ArrayList<>();
}
