package com.ddukddak.backend.reservation.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ReservationDTO {

    private Long roomId;
    private String roomName;
    private String reservedTime;

    public static ReservationDTO create(Long id, String roomName, String reservedTime) {
        ReservationDTO reservationDTO = new ReservationDTO();
        reservationDTO.setRoomId(id);
        reservationDTO.setRoomName(roomName);
        reservationDTO.setReservedTime(reservedTime);

        return reservationDTO;
    }
}