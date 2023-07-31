package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.chat.dto.PrivateMessage;
import com.ddukddak.backend.chat.dto.PrivateRoomInfo;
import com.ddukddak.backend.chat.dto.UniformDTO;
import com.ddukddak.backend.user.User;
import com.ddukddak.backend.user.UserRepository;
import com.ddukddak.backend.utils.Define;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class ChatTableService {

    private final ChatTableRepository chatTableRepository;
    private final UserRepository userRepository;
    private final PrivateChatRoomRepository privateChatRoomRepository;
    private final SimpMessagingTemplate template;
    private final EntityManager em;
    private int person;

    @Transactional
    public Long join(ChatTable chatTable){
        chatTableRepository.save(chatTable);
        return chatTable.getId();
    }

    @Transactional
    public ChatTable createTable(String intraId, PrivateChatRoom privateChatRoom) {
        User user = userRepository.findByName(intraId);
        ChatTable chatTable = ChatTable.createChatTable(user, privateChatRoom);
        user.getChatTables().add(chatTable);
        chatTableRepository.save(chatTable);
        return chatTable;
    }

    @Transactional
    public Long saveContents(String sender, String message, Long roomId) {
        ChatTable chatTable = chatTableRepository.findOne(roomId);
        PrivateChatRoom chatRoom = privateChatRoomRepository.findOne(chatTable.getPrivateChatRoom().getId());
        PrivateStorage privateStorage = new PrivateStorage(sender, message, chatRoom);
        chatRoom.addPrivateStorages(privateStorage);

        return chatTable.getId();
    }

    public List<PrivateRoomInfo> getAllRoomInfo() {
        List<PrivateRoomInfo> result = new ArrayList<>();
        List<ChatTable> chatTables = chatTableRepository.findAll();

        for (ChatTable table : chatTables) {
            PrivateChatRoom privateChatRoom = privateChatRoomRepository.findOne(table.getPrivateChatRoom().getId());
            PrivateRoomInfo privateRoomInfo = new PrivateRoomInfo(table.getId(), privateChatRoom.getRoomName(),
                    table.getHost(), privateChatRoom.getCreateTime(), privateChatRoom.getParticipantsNum());
            result.add(privateRoomInfo);
        }
        Collections.reverse(result);
        return result;
    }

    public Long restTime(LocalDateTime createTime) {
        return Define.EXPIRE_MIN - createTime.until(LocalDateTime.now(), ChronoUnit.MINUTES);
    }

    public ChatTable findOne(Long chatTableId){
        return chatTableRepository.findOne(chatTableId);
    }
    
    public List<PrivateMessage> findMessageInfo(Long tableId){
        ChatTable chatTable = chatTableRepository.findOne(tableId);
        PrivateChatRoom chatRoom = chatTable.getPrivateChatRoom();
        List<PrivateStorage> storages = chatRoom.getPrivateStorages();
        List<PrivateMessage> result = new ArrayList<>();
        User user = chatTable.getUser();
        user.getChatTables().add(chatTable);

        for(PrivateStorage storage : storages) {
            result.add(PrivateMessage.create(storage.getContents(), storage.getIntraId(), storage.getSendTime()));
        }
        return result;
    }

    public List<UniformDTO> getMessageInfo(Long tableId, String intraId) {
        person++;
        User user = userRepository.findByName(intraId);
        PrivateChatRoom privateChatRoom = chatTableRepository.findOne(tableId).getPrivateChatRoom();
        if (!user.isMaster()) {
            ChatTable chatTable = chatTableRepository.findByName(intraId);
            if (chatTable != null) {
                chatTable.setPrivateChatRoom(privateChatRoom);
            }
            else {
                createTable(intraId, privateChatRoom);
            }
        }
        List<PrivateStorage> storages = privateChatRoom.getPrivateStorages();
        List<UniformDTO> res = new ArrayList<>();
        log.info("who are you : " + intraId);

        for(PrivateStorage storage : storages) {
            res.add(new UniformDTO(storage.getIntraId(), storage.getContents(),
                    restTime(privateChatRoom.getCreateTime()), person));
        }
        return res;
    }

    public void remove(Long tableId) {
        ChatTable table = chatTableRepository.findOne(tableId);
        PrivateChatRoom room = privateChatRoomRepository.findOne(table.getPrivateChatRoom().getId());

        em.remove(room);
    }

    public UniformDTO create(Long roomId, String sender, String message, int people) {
        PrivateChatRoom room =  privateChatRoomRepository.findOne(roomId);
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
            if (expirationTime != null && expirationTime.isBefore(currentTime)) {
                log.info("expiration time is " + expirationTime);
                PrivateChatRoom room = table.getPrivateChatRoom();
                privateChatRoomRepository.delete(room);
                log.info("private_room " + room.getRoomName() + " 방을 지웠습니다!");

                log.info("table id " + id + " is deleted!");
                template.convertAndSend("sub/chat/room/" + id, HttpStatus.OK);
            }
        }
    }

//    public void perfectTrashBin(Long tableId) {
//
//    }
}
