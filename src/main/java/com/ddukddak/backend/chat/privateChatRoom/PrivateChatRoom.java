package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.chat.ChatTable;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class PrivateChatRoom {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "private_chat_room_id")
    private Long id;
    
    private String roomName;
    
    private LocalDateTime createTime;

    private Long restTime;

    @OneToMany(mappedBy = "privateChatRoom")
    private List<ChatTable> users = new ArrayList<>();

    public PrivateChatRoom(String name){
        this.roomName = name;
        this.createTime = LocalDateTime.now();
        this.restTime = 15L;
    }

}
