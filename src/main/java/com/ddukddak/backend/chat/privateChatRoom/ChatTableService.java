package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.chat.dto.PrivateMessage;
import com.ddukddak.backend.chat.dto.PrivateRoomInfo;
import com.ddukddak.backend.chat.dto.UniformDTO;
import com.ddukddak.backend.reservation.Enum.ReservationStatus;
import com.ddukddak.backend.reservation.Reservation;
import com.ddukddak.backend.reservation.ReservationRepository;
import com.ddukddak.backend.user.User;
import com.ddukddak.backend.user.UserRepository;
import com.ddukddak.backend.utils.Define;
import com.ddukddak.backend.utils.HttpCode;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChatTableService {

    private final ChatTableRepository chatTableRepository;
    private final UserRepository userRepository;
    private final PrivateChatRoomRepository privateChatRoomRepository;
    private final SimpMessagingTemplate template;
    private final ReservationRepository reservationRepository;
    private int person;

    @Transactional
    public Long join(ChatTable chatTable) {
        chatTableRepository.save(chatTable);
        return chatTable.getId();
    }

    @Transactional
    public ChatTable createTable(String intraId, PrivateChatRoom privateChatRoom) {
        User user = userRepository.findByName(intraId);
        ChatTable chatTable = ChatTable.createChatTable(user, privateChatRoom);
        user.getChatTables().add(chatTable);
        chatTableRepository.save(chatTable);
        log.info("null, name : " + chatTable.getPrivateChatRoom().getRoomName());
        return chatTable;
    }

    @Transactional
    public Long saveContents(String sender, String message, Long roomId) {
        ChatTable chatTable = chatTableRepository.findOne(roomId);
        PrivateChatRoom chatRoom = privateChatRoomRepository.findOne(chatTable.getPrivateChatRoom().getId());
        PrivateStorage privateStorage = new PrivateStorage(sender, message, chatRoom);
        ChatTable userTable = userRepository.findByName(sender).getChatTables().get(0);
        privateStorage.setGuestId(userTable.getId());
        chatRoom.addPrivateStorages(privateStorage);

        return chatTable.getId();
    }

    public List<PrivateRoomInfo> getAllRoomInfo() {
        List<PrivateRoomInfo> result = new ArrayList<>();
        List<ChatTable> chatTables = chatTableRepository.findAll();

        for (ChatTable table : chatTables) {
            if (table.getHost() != null) {
                PrivateChatRoom privateChatRoom = privateChatRoomRepository.findOne(table.getPrivateChatRoom().getId());
                if (privateChatRoom.getReserved().equals(ReservationStatus.CANCEL)) {
                    PrivateRoomInfo privateRoomInfo = new PrivateRoomInfo(table.getId(), privateChatRoom.getRoomName(),
                            table.getHost(), privateChatRoom.getCreateTime(), privateChatRoom.getParticipantsNum());
                    result.add(privateRoomInfo);
                }
            }
        }
        Collections.reverse(result);
        return result;
    }

    public Long restTime(LocalDateTime createTime) {
        return Define.EXPIRE_MIN - createTime.until(LocalDateTime.now(), ChronoUnit.MINUTES);
    }

    public ChatTable findOne(Long chatTableId) {
        return chatTableRepository.findOne(chatTableId);
    }

    public List<PrivateMessage> findMessageInfo(Long tableId) {
        ChatTable chatTable = chatTableRepository.findOne(tableId);
        PrivateChatRoom chatRoom = chatTable.getPrivateChatRoom();
        List<PrivateStorage> storages = chatRoom.getPrivateStorages();
        List<PrivateMessage> result = new ArrayList<>();
        User user = chatTable.getUser();
        user.getChatTables().add(chatTable);

        for (PrivateStorage storage : storages) {
            result.add(PrivateMessage.create(storage.getContents(), storage.getIntraId(), storage.getSendTime()));
        }
        return result;
    }

    public List<UniformDTO> getMessageInfo(Long tableId, String intraId) {
        User user = userRepository.findByName(intraId);
        PrivateChatRoom privateChatRoom = chatTableRepository.findOne(tableId).getPrivateChatRoom();
        if (!user.isMaster()) {
            privateChatRoom.setParticipantsNum(privateChatRoom.getParticipantsNum() + 1);
            user.setPrivateChatRoomId(privateChatRoom.getId());
        }
        log.info("before privateRoom id : " + privateChatRoom.getId());
        List<ChatTable> tables = user.getChatTables();
        if (!user.isMaster()) {
            if (tables.size() == 0) {
                createTable(intraId, privateChatRoom);
            } else {
                tables.get(0).setPrivateChatRoom(privateChatRoom);
            }
        }
        List<PrivateStorage> storages = privateChatRoom.getPrivateStorages();
        List<UniformDTO> res = new ArrayList<>();
        log.info("who are you : " + intraId);

        for (PrivateStorage storage : storages) {
            res.add(new UniformDTO(storage.getIntraId(), storage.getContents(),
                    restTime(privateChatRoom.getCreateTime()), privateChatRoom.getParticipantsNum()));
        }
        if (res.isEmpty()) {
            res.add(new UniformDTO(null, null, null, privateChatRoom.getParticipantsNum()));
        }
        template.convertAndSend("/sub/chat/room" + tableId, privateChatRoom.getParticipantsNum());
        return res;
    }


    public UniformDTO create(Long roomId, String sender, String message, int people) {
        PrivateChatRoom room = privateChatRoomRepository.findOne(roomId);
        UniformDTO uniformDTO = new UniformDTO();

        uniformDTO.setSender(sender);
        uniformDTO.setMessage(message);
        uniformDTO.setTime(LocalDateTime.now());
        uniformDTO.setRemainingTime(restTime(room.getCreateTime()));
        uniformDTO.setParticipantsNum(people);

        return uniformDTO;
    }

    public List<User> findUsersInRoom(Long roomId) {
        List<User> users = new ArrayList<>();
        List<ChatTable> tables = chatTableRepository.findAll();

        for (ChatTable table : tables) {
            if (table.getPrivateChatRoom().getId().equals(roomId)) {
                log.info("user name ??????" + table.getUser().getIntraId());
                users.add(table.getUser());
            }
        }
        return users;
    }


    @Scheduled(fixedRate = 30000) //30초
    public void checkExpiredChatRooms() {
        log.info("30초마다 자동 쓰레기통이 돈다.....");
        LocalDateTime currentTime = LocalDateTime.now();
        List<ChatTable> tables = chatTableRepository.findAll();
        for (ChatTable table : tables) {
            Long id = table.getId();
            LocalDateTime expirationTime = table.getPrivateChatRoom().getExpirationTime();
            log.info("after.... : " + expirationTime);

            if (expirationTime != null && table.getHost() != null) {
                Duration duration = Duration.between(currentTime, expirationTime);
                Long leftMinute = duration.toSeconds();
                log.info("" + leftMinute);
                if (leftMinute < 60 && leftMinute > 35) {
                    log.info("들어와줘");
                    template.convertAndSend("/sub/chat/room/" + id, HttpCode.BEFORE_DESTORY);
                } else if (expirationTime.isBefore(currentTime)) {
                    log.info("expiration time is " + expirationTime);
                    PrivateChatRoom room = table.getPrivateChatRoom();
                    User user = table.getUser();
                    user.setMaster(false);
                    List<Reservation> reservations = user.getReservations();

                    // 만약 내가 예약한 내역이 있다면, privateRoom이 폭 파 되었기에 List 에서 reservation 지워주고, entity 삭제
                    if (reservations.size() > 0) {
                        for (Reservation reservation : reservations) {
                            if (reservation.getStatus() == ReservationStatus.RESERVE &&
                                    reservation.getPrivate_chat_room_id().equals(room.getId())) {
                                reservations.remove(reservation);
                                reservationRepository.delete(reservation);
                            }
                        }
                    }
                    privateChatRoomRepository.delete(room);
                    log.info("private_room " + room.getRoomName() + " 방을 지웠습니다!");

                    log.info("table id " + id + " is deleted!");
                    template.convertAndSend("/sub/chat/room/" + id, HttpCode.AFTER_EXPIRE_TIME);
                }
            }
        }
    }

    public void destroy(Long id) {
        ChatTable table = chatTableRepository.findOne(id);
        User user = table.getUser();
        PrivateChatRoom privateChatRoom = table.getPrivateChatRoom();
        if (privateChatRoom.getReserved() == ReservationStatus.CANCEL) {
            user.setMaster(false);
            privateChatRoomRepository.delete(privateChatRoom);
        } else {
            List<User> users = this.findUsersInRoom(privateChatRoom.getId());
            for (User my : users) {
                chatTableRepository.delete(my.getChatTables().get(0));
            }
        }
    }

    public void leave(String intraId) {
        User user = userRepository.findByName(intraId);
        ChatTable table = user.getChatTables().get(0);
        PrivateChatRoom privateChatRoom = table.getPrivateChatRoom();
        privateChatRoom.setParticipantsNum(privateChatRoom.getParticipantsNum() - 1);
        chatTableRepository.delete(table);
    }
}
