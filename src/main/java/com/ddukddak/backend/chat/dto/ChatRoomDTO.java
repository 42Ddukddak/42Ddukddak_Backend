package com.ddukddak.backend.chat.dto;

import lombok.Data;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

//@Getter @Setter
@Data
public class ChatRoomDTO {

    private String roomId;
    private String name;
    private Set<WebSocketSession> session = new HashSet<>();

    public static ChatRoomDTO create(String name) {
        ChatRoomDTO chatRoom = new ChatRoomDTO();

//        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.roomId = "1";
        chatRoom.name = name;
        return chatRoom;
    }
}
