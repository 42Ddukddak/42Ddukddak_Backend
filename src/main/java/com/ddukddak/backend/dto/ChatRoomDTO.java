package com.ddukddak.backend.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Getter @Setter
public class ChatRoomDTO {

    private String roomId;
    private String name;
    private Set<WebSocketSession> session = new HashSet<>();

    public static ChatRoomDTO create(String name) {
        ChatRoomDTO chatRoom = new ChatRoomDTO();

        chatRoom.roomId = UUID.randomUUID().toString();
        chatRoom.name = name;
        return chatRoom;
    }
}
