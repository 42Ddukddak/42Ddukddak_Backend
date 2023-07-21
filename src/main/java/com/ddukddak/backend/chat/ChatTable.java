package com.ddukddak.backend.chat;

import com.ddukddak.backend.chat.Storage;
import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoom;
import com.ddukddak.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
public class ChatTable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_table_id")
    private Long id;

    @Embedded
    private Storage storage;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "private_chat_room_id")
    private PrivateChatRoom privateChatRoom;

    public ChatTable(User user, PrivateChatRoom privateChatRoom){
        this.user = user;
        this.privateChatRoom = privateChatRoom;
    }
}
