package com.ddukddak.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class ChatMessageDTO {

    public enum MessageType {
        ENTER, TALK
    }

    private String roomId;
    private String sender;
    private String message;
}
