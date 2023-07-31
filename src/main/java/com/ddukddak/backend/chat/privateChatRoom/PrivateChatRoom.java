package com.ddukddak.backend.chat.privateChatRoom;

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

    private int participantsNum;

    private LocalDateTime expirationTime;

    @OneToMany(mappedBy = "privateChatRoom", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ChatTable> chatTables = new ArrayList<>();

    public PrivateChatRoom(String name){
        this.roomName = name;
        this.createTime = LocalDateTime.now();
        this.participantsNum = 1;
        this.expirationTime = createTime.plusMinutes(15);
//        this.expirationTime = createTime.plusSeconds(30);
    }

}
