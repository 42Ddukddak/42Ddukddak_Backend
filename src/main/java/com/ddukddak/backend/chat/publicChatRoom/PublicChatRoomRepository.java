package com.ddukddak.backend.chat.publicChatRoom;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@RequiredArgsConstructor
public class PublicChatRoomRepository {

    private final EntityManager em;

    @Transactional
    public void save(PublicChatRoom chatRoom) {
        em.persist(chatRoom);
    }

    @Transactional
    public PublicChatRoom findOne(Long id){
        return em.find(PublicChatRoom.class, id);
    }

//    @Transactional
//    public void clearStorage(){
//        em.clear();
//    }

}
