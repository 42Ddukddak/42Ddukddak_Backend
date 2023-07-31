package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.user.User;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@NoArgsConstructor
public class ChatTable {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "chat_table_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "private_chat_room_id")
    private PrivateChatRoom privateChatRoom;

    private String host;
    
    public static ChatTable createChatTable(User user, PrivateChatRoom privateChatRoom){
       ChatTable chatTable = new ChatTable();
       chatTable.setUser(user);
       chatTable.setPrivateChatRoom(privateChatRoom);

       return chatTable;
    }

}
