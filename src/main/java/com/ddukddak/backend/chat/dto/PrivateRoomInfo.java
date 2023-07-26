package com.ddukddak.backend.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDateTime;


@Getter @Setter
@NoArgsConstructor
public class PrivateRoomInfo {
    Long roomId;
    String roomName;
    String login;
    Long remainingTime;
    int participantsNum;

    public PrivateRoomInfo(Long roomId, String roomName, String host, Long restTime, int participantsNum) {
        this.roomId = roomId;
        this.roomName = roomName;
        this.login = host;
        this.remainingTime = restTime;
        this.participantsNum = participantsNum;
    }
    public PrivateRoomInfo(Long roomId, String roomName, String host, LocalDateTime time, int participantsNum){
        this.roomId = roomId;
        this.roomName = roomName;
        this.login = host;
        this.remainingTime = Duration.between(time, LocalDateTime.now()).getSeconds() / 60;
        this.participantsNum = participantsNum;
    }
}
