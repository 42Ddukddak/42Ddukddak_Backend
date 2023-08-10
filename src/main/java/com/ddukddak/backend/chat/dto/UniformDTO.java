package com.ddukddak.backend.chat.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;


/*
* sender?: string;
  message?: string;
  time?: string;
  remainingTime: number;
  participantsNum: number;
* */
@Getter
@Setter
@NoArgsConstructor
public class UniformDTO {

    private String sender;
    private String message;
    private LocalDateTime time; // 메세지 보낸 시간

    private Long remainingTime;
    private int participantsNum;

    public UniformDTO(String sender, String message, Long remainingTime, int people) {
        this.sender = sender;
        this.message = message;
        this.time = LocalDateTime.now();
        this.remainingTime = remainingTime;
        this.participantsNum = people;
    }

    public static UniformDTO create(String sender, String message, LocalDateTime time,
                                    Long remainingTime, int participantsNum) {
        UniformDTO uniformDTO = new UniformDTO();
        uniformDTO.setSender(sender);
        uniformDTO.setMessage(message);
        uniformDTO.setTime(time);
        uniformDTO.setRemainingTime(remainingTime);
        uniformDTO.setParticipantsNum(participantsNum);

        return uniformDTO;
    }


}
