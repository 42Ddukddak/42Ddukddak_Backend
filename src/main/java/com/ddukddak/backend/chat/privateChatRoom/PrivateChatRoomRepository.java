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

    public PrivateChatRoom findOne(Long id) {
        return em.find(PrivateChatRoom.class, id);
    }

    public List<PrivateChatRoom> findAll() {
        return em.createQuery("select r from PrivateChatRoom r", PrivateChatRoom.class)
                .getResultList();
    }

    @Transactional
    public Long save(PrivateChatRoom privateChatRoom, User user) throws Exception{
//        if (user.isMaster() || user.isBanned())
//            throw new Exception("돌아가");
        em.persist(privateChatRoom);
        return privateChatRoom.getId();
    }

    public void delete(PrivateChatRoom privateChatRoom) {
        em.remove(privateChatRoom);
    }


}
