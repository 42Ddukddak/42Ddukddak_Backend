package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.chat.dto.ChatRoomDTO;
import com.ddukddak.backend.user.User;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Repository
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class PrivateChatRoomRepository {

    private final EntityManager em;
    private Map<String, ChatRoomDTO> chatRoomDTOMap;
//    private final PrivateChatRoom privateChatRoom;

    @PostConstruct
    private void init() {
        chatRoomDTOMap = new LinkedHashMap<>();
    }

    public PrivateChatRoom findOne(Long id) {
        return em.find(PrivateChatRoom.class, id);
    }

    public List<PrivateChatRoom> findAll() {
        return em.createQuery("select r from PrivateChatRoom r", PrivateChatRoom.class)
                .getResultList();
    }

//    public PrivateChatRoom findById(Long roomId) {
//        return em.createQuery("select r from PrivateChatRoom r where r.id = :roomId", PrivateChatRoom.class)
//                .setParameter("")
//    }

    public List<ChatRoomDTO> findAllRooms() {
        List<ChatRoomDTO> result = new ArrayList<>(chatRoomDTOMap.values());
        Collections.reverse(result); // 최근순 정렬

        return result;
    }

    public ChatRoomDTO findRoomByName(String id) {
        return chatRoomDTOMap.get(id);
    }

    public ChatRoomDTO createChatRoomDTO(String name) {
        ChatRoomDTO room = ChatRoomDTO.create(name);
        chatRoomDTOMap.put(room.getRoomId(), room);

        return room;
    }

    @Transactional
    public Long save(PrivateChatRoom privateChatRoom, User user) throws Exception{
//        if (user.isMaster() || user.isBanned())
//            throw new Exception("돌아가");
        em.persist(privateChatRoom);
        return privateChatRoom.getId();
    }


}
