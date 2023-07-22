package com.ddukddak.backend.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class ChatMessageDTO {

    public enum MessageType {
        ENTER, TALK, LEAVE
    }

    private String roomId;
    private String sender;
    private String message;
    private LocalDateTime time;
    private MessageType type;
}
