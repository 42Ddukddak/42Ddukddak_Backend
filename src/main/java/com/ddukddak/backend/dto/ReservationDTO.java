package com.ddukddak.backend.dto;

import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class ReservationDTO {

    private String roomName;
    private LocalDateTime reservedTime;

    public ReservationDTO(String roomName, LocalDateTime reservedTime){
        this.roomName = roomName;
        this.reservedTime = reservedTime;
    }
}