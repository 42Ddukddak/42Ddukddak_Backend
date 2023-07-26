package com.ddukddak.backend.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter @Setter
public class PrivateMessage {

    private String message;
    private String sender;
    private LocalDateTime time;

    public static PrivateMessage create(String contents, String sender, LocalDateTime time) {
        PrivateMessage privateMessage = new PrivateMessage();

        privateMessage.message = contents;
        privateMessage.sender = sender;
        privateMessage.time = time;

        return privateMessage;
    }
}
