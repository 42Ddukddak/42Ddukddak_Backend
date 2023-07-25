package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.chat.privateChatRoom.ChatTable;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class ChatTableRepository {

    private final EntityManager em;

    public void save(ChatTable chatTable){
        em.persist(chatTable);
    }

    public ChatTable findOne(Long id){
        return em.find(ChatTable.class, id);
    }
}
