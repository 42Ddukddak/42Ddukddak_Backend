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
import org.springframework.cglib.core.Local;
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

    public void create(User user, PrivateChatRoom privateChatRoom) {
        ChatTable chatTable = ChatTable.createChatTable(user, privateChatRoom);
        user = userRepository.findOne(user.getId());

    }

    @Transactional
    public Long saveContents(String sender, String message, Long roomId) {
        ChatTable chatTable = chatTableRepository.findOne(roomId);
        PrivateStorage privateStorage = new PrivateStorage(sender, message, chatTable);
        chatTable.addPrivateStorages(privateStorage);

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
        List<PrivateStorage> storages = chatTable.getPrivateStorages();
        List<PrivateMessage> result = new ArrayList<>();
        User user = chatTable.getUser();
        user.getChatTables().add(chatTable);

        for(PrivateStorage storage : storages) {
            result.add(PrivateMessage.create(storage.getContents(), storage.getIntraId(), storage.getSendTime()));
        }
        return result;
    }

    public List<UniformDTO> getMessageInfo(Long tableId) {
        person++;
        ChatTable chatTable = chatTableRepository.findOne(tableId);
        PrivateChatRoom privateChatRoom = chatTable.getPrivateChatRoom();
        List<PrivateStorage> storages = chatTable.getPrivateStorages();
        List<UniformDTO> res = new ArrayList<>();
        User user = chatTable.getUser();
        log.info("who are you : " + user.getIntraId());
        user.addChatTable(chatTable); // List에 add, id 따로 저장하도록 합쳤습니다.

        for(PrivateStorage storage : storages) {
            res.add(new UniformDTO(storage.getIntraId(), storage.getContents(),
                    restTime(privateChatRoom.getCreateTime()), person));
        }
        //여기에서 새로운 chatTable을 저장하는 로직이 필요함.. 들어온 user 정보와 함께

        return res;
    }

    public void remove(Long tableId) {
        ChatTable table = chatTableRepository.findOne(tableId);
        PrivateChatRoom room = privateChatRoomRepository.findOne(table.getPrivateChatRoom().getId());
        table.getUser().getChatTables().remove(0);

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
                PrivateChatRoom room = privateChatRoomRepository.findOne(table.getPrivateChatRoom().getId());
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
