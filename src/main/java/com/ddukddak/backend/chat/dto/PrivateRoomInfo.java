package com.ddukddak.backend.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;


@Getter @Setter
@NoArgsConstructor
public class PrivateRoomInfo {

    private Long roomId;
    private String roomName;
    private String login;
    private Long remainingTime;
    private int participantsNum;

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
        this.remainingTime = timeIs(time);
        this.participantsNum = participantsNum;
    }

    public Long timeIs(LocalDateTime createTime) {
        LocalDateTime now = LocalDateTime.now();
        Long remainingMin = createTime.until(now, ChronoUnit.MINUTES);
        Long remainingSec = createTime.until(now, ChronoUnit.SECONDS) % 60;

        Long totalRemainingMin = 15 - remainingMin;
        Long totalRemainingSec = 60 - remainingSec;

        return totalRemainingMin;
    }
}
