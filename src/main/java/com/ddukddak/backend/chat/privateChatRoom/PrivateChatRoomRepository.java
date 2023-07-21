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

    @PostConstruct
    private void init() {
        chatRoomDTOMap = new LinkedHashMap<>();
    }

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
    public Long save(PrivateChatRoom privateChatRoom) {
        em.persist(privateChatRoom);
        return privateChatRoom.getId();
    }
}
