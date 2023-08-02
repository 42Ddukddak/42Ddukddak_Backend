package com.ddukddak.backend.reservation;

import com.ddukddak.backend.chat.privateChatRoom.*;
import com.ddukddak.backend.reservation.Enum.ReservationStatus;
import com.ddukddak.backend.reservation.dto.ReservationDTO;
import com.ddukddak.backend.user.User;
import com.ddukddak.backend.user.UserService;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ReservationService {

    private final ChatTableService chatTableService;
    private final ChatTableRepository chatTableRepository;
    private final ReservationRepository reservationRepository;
    private final UserService userService;
    private final PrivateChatRoomRepository privateChatRoomRepository;
    @Transactional
    public void reservation(Long tableId) {
        ChatTable table = chatTableRepository.findOne(tableId);
        PrivateChatRoom privateChatRoom = table.getPrivateChatRoom();
        privateChatRoom.setExpirationTime(privateChatRoom.getExpirationTime().plusHours(3));
        List<User> users = chatTableService.findUsersInRoom(privateChatRoom.getId());
        for (User user : users) {
            log.info("시 바 intra?" + user.getIntraId());
            reservationRepository.save(Reservation.createReservation(user, privateChatRoom.getRoomName(), privateChatRoom.getId()));
        }
    }

    //예약을 취소했을 때 private_chat_room 을 지우고, reservation도 지우는 로직
    @Transactional
    public void cancelReservation(Long reservedId) {
        Reservation reservation = reservationRepository.findOne(reservedId);
        List<Reservation> reservationList = reservation.getUser().getReservations();
        for (Reservation userReservation : reservationList) {
            if (userReservation.equals(reservation)){
                reservationList.remove(userReservation);
                reservationRepository.delete(userReservation);
            }
        }
    }

    @Transactional
    public List<ReservationDTO> reservationList(String intraId){
        User user = userService.findByName(intraId);
        List<Reservation> reservations = user.getReservations();
        List<ReservationDTO> result = new ArrayList<>();
        for (Reservation userReservation : reservations){
            if (userReservation.getStatus() == ReservationStatus.RESERVE) {
                PrivateChatRoom privateChatRoom = privateChatRoomRepository.findOne(userReservation.getPrivate_chat_room_id());
                result.add(new ReservationDTO(userReservation.getChatRoomName(), userReservation.getReservationTime(),
                        privateChatRoom.getPrivateStorages()));
            }
        }
        return result;
    }

    @Transactional
    public int reservationNumber(String intraId){
        User user = userService.findByName(intraId);
        List<Reservation> reservations = user.getReservations();
        int result = 0;
        for (Reservation reservation : reservations){
            if (reservation.getStatus() == ReservationStatus.RESERVE){
                result += 1;
            }
        }
        return result;
    }
}
