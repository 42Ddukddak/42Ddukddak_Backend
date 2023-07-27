package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.chat.privateChatRoom.ChatTable;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
public class ChatTableRepository {

    private final EntityManager em;

    public void save(ChatTable chatTable){
        em.persist(chatTable);
    }

    public ChatTable findOne(Long id){
        return em.find(ChatTable.class, id);
    }

    public List<ChatTable> findAll() {
        return em.createQuery("select c from ChatTable c", ChatTable.class)
                .getResultList();
    }

    public void delete(ChatTable chatTable) {
        em.remove(chatTable);
    }
}
