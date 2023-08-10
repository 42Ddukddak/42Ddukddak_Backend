package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.api.entity.Token;
import com.ddukddak.backend.chat.privateChatRoom.ChatTable;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@RequiredArgsConstructor
@Transactional
@Slf4j
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

    public ChatTable findByName(String intraId) {
        return em.createQuery("select m from ChatTable m where m.user.intraId = :intraId", ChatTable.class)
                .setParameter("intraId", intraId)
                .getSingleResult();
    }
}
