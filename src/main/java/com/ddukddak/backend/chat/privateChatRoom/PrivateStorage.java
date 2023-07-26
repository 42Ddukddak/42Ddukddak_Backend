package com.ddukddak.backend.chat.privateChatRoom;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor
public class PrivateStorage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "private_storage_id")
    private Long id;

    private String contents;

    private String intraId;

    private Long roomId;

    private String roomName;

    private LocalDateTime sendTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_table_id")
    private ChatTable chatTable;

    public PrivateStorage(String sender, String message, ChatTable chatTable) {
        this.intraId = sender;
        this.contents = message;
        this.roomId = chatTable.getPrivateChatRoom().getId();
        this.sendTime = LocalDateTime.now();
        this.chatTable = chatTable;
    }
}
