package com.ddukddak.backend.reservation;

import com.ddukddak.backend.chat.privateChatRoom.ChatTable;
import com.ddukddak.backend.chat.privateChatRoom.ChatTableService;
import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoom;
import com.ddukddak.backend.user.User;
import com.ddukddak.backend.chat.privateChatRoom.ChatTableRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ChatTableService chatTableService;
    private final ChatTableRepository chatTableRepository;
    private final ReservationRepository reservationRepository;

    @Transactional
    public void reservation(Long tableId) {
        ChatTable table = chatTableRepository.findOne(tableId);
        PrivateChatRoom privateChatRoom = table.getPrivateChatRoom();
        List<User> users = chatTableService.findUsersInRoom(privateChatRoom.getId());
        for (User user : users) {
            log.info("시 바 intra?" + user.getIntraId());
            reservationRepository.save(Reservation.createReservation(user, privateChatRoom.getRoomName()));
        }
    }

    @Transactional
    public void cancelReservation(Long reservedId) {
        Reservation reservation = reservationRepository.findOne(reservedId);
        reservation.cancel();
    }
}
