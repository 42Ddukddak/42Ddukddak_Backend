package com.ddukddak.backend.chat.privateChatRoom;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class PrivateStorage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "private_storage_id")
    private Long id;

    private String contents;

    private String intraId;

    private Long guestId;

    private LocalDateTime sendTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "private_chat_room_id")
    private PrivateChatRoom privateChatRoom;

    public PrivateStorage(String sender, String message, PrivateChatRoom privateChatRoom) {
        this.intraId = sender;
        this.contents = message;
        this.sendTime = LocalDateTime.now();
        this.privateChatRoom = privateChatRoom;
    }
}
