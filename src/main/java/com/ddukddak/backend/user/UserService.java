package com.ddukddak.backend.user;

import com.ddukddak.backend.chat.privateChatRoom.ChatTable;
import com.ddukddak.backend.chat.privateChatRoom.ChatTableRepository;
import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoom;
import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoomRepository;
import com.ddukddak.backend.chat.publicChatRoom.PublicChatRoom;
import com.ddukddak.backend.reservation.Reservation;
import com.ddukddak.backend.reservation.dto.ReservationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PrivateChatRoomRepository privateChatRoomRepository;
    private final ChatTableRepository chatTableRepository;

    public Long join(User user) {
        userRepository.save(user);
        return user.getId();
    }

    public User createPublicChatRoom(String intraId, PublicChatRoom publicChatRoom) {
        User user = new User(intraId, publicChatRoom);
        userRepository.save(user);
        return user;
    }

    public Long createPrivateChatRoom(String intraId, String roomName) throws Exception {
        User user = userRepository.findByName(intraId);
        user.setMaster(true);
        PrivateChatRoom privateChatRoom = new PrivateChatRoom(roomName);
        privateChatRoomRepository.save(privateChatRoom, user);
//        if (privateChatRoom.getExpirationTime() != null) { // 새로운 스레드를 생성한 후 효율적으로 방을 삭제하기 위한 메소드...ㅠㅠ
//            Duration duration = Duration.between(privateChatRoom.getCreateTime(), privateChatRoom.getExpirationTime());
//            long delayInMillis = duration.toMillis();
//
//            taskScheduler.schedule(new RoomDestructionTask(privateChatRoom, privateChatRoomRepository), new Date(System.currentTimeMillis() + delayInMillis));
//        }
        ChatTable chatTable = ChatTable.createChatTable(user, privateChatRoom);
        chatTable.setHost(user.getIntraId());
        chatTableRepository.save(chatTable);
        user.addChatTable(chatTable);

        return chatTable.getId();
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }

    public User findByName(String name) {
        return userRepository.findByName(name);
    }

    public List<ReservationDTO> findReservation(String name) {
        List<Reservation> reservations = userRepository.findByName(name).getReservations();
        List<ReservationDTO> result = new ArrayList<>();
        for (Reservation reservation : reservations) {
            result.add(ReservationDTO.create(reservation.getId(), reservation.getChatRoomName(),
                    reservation.getReservationTime()));
        }
        return result;
    }

}
