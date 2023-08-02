package com.ddukddak.backend.reservation.dto;

import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoom;
import com.ddukddak.backend.chat.privateChatRoom.PrivateStorage;
import lombok.Getter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter
public class ReservationDTO {

    private String roomName;
    private String reservedTime;
    private List<PrivateStorage> privateStorages;
    public ReservationDTO(String roomName, LocalDateTime reservedTime, List<PrivateStorage> privateStorages){
        this.roomName = roomName;
        this.reservedTime = reservedTime.format(DateTimeFormatter.BASIC_ISO_DATE);
        this.privateStorages = privateStorages;
    }
}