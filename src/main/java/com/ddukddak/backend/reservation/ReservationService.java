package com.ddukddak.backend.reservation;

import com.ddukddak.backend.chat.dto.UniformDTO;
import com.ddukddak.backend.chat.privateChatRoom.*;
import com.ddukddak.backend.reservation.Enum.ReservationStatus;
import com.ddukddak.backend.user.User;
import com.ddukddak.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
    public void reservation(Long tableId, String time) {
        ChatTable table = chatTableRepository.findOne(tableId);
        PrivateChatRoom privateChatRoom = table.getPrivateChatRoom();
        privateChatRoom.setExpirationTime(privateChatRoom.getExpirationTime().plusHours(3));
        privateChatRoom.setReserved(ReservationStatus.RESERVE);
        List<User> users = chatTableService.findUsersInRoom(privateChatRoom.getId());
        for (User user : users) {
            log.info("시 바 intra?" + user.getIntraId());
            reservationRepository.save(Reservation.createReservation(user, privateChatRoom.getRoomName(),
                    privateChatRoom.getId(), time));
        }
    }

    //예약을 취소했을 때 private_chat_room 을 지우고, reservation도 지우는 로직
    @Transactional
    public void cancelReservation(Long reservedId) {
        Reservation reservation = reservationRepository.findOne(reservedId);
        List<Reservation> reservationList = reservation.getUser().getReservations();
        for (Reservation userReservation : reservationList) {
            if (userReservation.equals(reservation)) {
                reservationList.remove(userReservation);
                reservationRepository.delete(userReservation);
            }
        }
    }

    @Transactional
    public List<UniformDTO> reservationList(Long id) {
        Reservation reservation = reservationRepository.findOne(id);
        PrivateChatRoom privateChatRoom = privateChatRoomRepository.findOne(reservation.getPrivate_chat_room_id());
        List<PrivateStorage> privateStorages = privateChatRoom.getPrivateStorages();
        List<UniformDTO> result = new ArrayList<>();
        for (PrivateStorage privateStorage : privateStorages) {
            result.add(UniformDTO.create(privateStorage.getIntraId(), privateStorage.getContents(),
                    privateStorage.getSendTime(), privateChatRoom.getRestTime(), privateChatRoom.getParticipantsNum()));
        }
        return result;
    }

    @Transactional
    public int reservationNumber(String intraId) {
        User user = userService.findByName(intraId);
        List<Reservation> reservations = user.getReservations();
        int result = 0;
        for (Reservation reservation : reservations) {
            if (reservation.getStatus() == ReservationStatus.RESERVE) {
                result += 1;
            }
        }
        return result;
    }
}
