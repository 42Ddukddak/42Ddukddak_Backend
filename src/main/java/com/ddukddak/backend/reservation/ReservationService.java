package com.ddukddak.backend.reservation;

import com.ddukddak.backend.chat.ChatTable;
import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoom;
import com.ddukddak.backend.user.User;
import com.ddukddak.backend.chat.ChatTableRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
