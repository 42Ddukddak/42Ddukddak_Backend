package com.ddukddak.backend.service;

import com.ddukddak.backend.domain.ChatTable;
import com.ddukddak.backend.domain.PrivateChatRoom;
import com.ddukddak.backend.domain.Reservation;
import com.ddukddak.backend.domain.User;
import com.ddukddak.backend.repository.ChatTableRepository;
import com.ddukddak.backend.repository.ReservationRepository;
import com.ddukddak.backend.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ReservationService {

    private final EntityManager em;
    private final ChatTableRepository chatTableRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public Long reservation(Long chatTableId){
        ChatTable chatTable = chatTableRepository.findOne(chatTableId);
        User user = chatTable.getUser();
        PrivateChatRoom privateChatRoom = chatTable.getPrivateChatRoom();
        Reservation reservation = Reservation.createReservation(user, privateChatRoom.getRoomName());
        reservationRepository.save(reservation);
        return reservation.getId();
    }

    @Transactional
    public void cancelReservation(Long reservedId){
        Reservation reservation = reservationRepository.findOne(reservedId);
        reservation.cancel();
    }
}
