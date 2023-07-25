package com.ddukddak.backend.chat.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;


@Getter @Setter
public class PrivateRoomInfo {
    Long roomId;
    String roomName;
    String host;
    LocalDateTime remainingTime;
    int participantsNum;

    public PrivateRoomInfo(Long roomId, String roomName, String host, int participantsNum){
        this.roomId = roomId;
        this.roomName = roomName;
        this.host = host;
        this.remainingTime = LocalDateTime.now();
        this.participantsNum = participantsNum;
    }
}
