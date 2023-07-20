package com.ddukddak.backend.reservation.dto;

import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
public class ReservationDTO {

    private String roomName;
    private String reservedTime;

    public ReservationDTO(String roomName, LocalDateTime reservedTime){
        this.roomName = roomName;
        this.reservedTime = reservedTime.format(DateTimeFormatter.BASIC_ISO_DATE);
    }
}