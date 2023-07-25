package com.ddukddak.backend.chat.privateChatRoom;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Fetch;

@Entity
@Getter @Setter
public class PrivateStorage {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "private_storage_id")
    Long id;

    String contents;

    String intraId;

    Long roomId;

    String roomName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_table_id")
    private ChatTable chatTable;

    public PrivateStorage(String sender, String message, String roomId) {
        this.intraId = sender;
        this.contents = message;
        this.roomId = Long.parseLong(roomId);
    }
}
