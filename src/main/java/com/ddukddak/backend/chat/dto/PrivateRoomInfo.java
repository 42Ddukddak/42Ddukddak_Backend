package com.ddukddak.backend.chat.dto;

import lombok.Getter;
import lombok.Setter;


@Getter @Setter
public class PrivateRoomInfo {
    Long roomId;
    String roomName;
    String master;
    int remainingTime;
    int participantsNum;

    public PrivateRoomInfo(Long roomId, String roomName, String master, int remainingTime, int participantsNum){
        this.roomId = roomId;
        this.roomName = roomName;
        this.master = master;
        this.remainingTime = remainingTime;
        this.participantsNum = participantsNum;
    }
}
