package com.ddukddak.backend.chat.privateChatRoom;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

//@Component
//@RequiredArgsConstructor
//public class RoomDestructionTask implements Runnable {
//
//    @PersistenceContext
//    private EntityManager em;
//    private PrivateChatRoom privateChatRoom;
//    private PrivateChatRoomRepository privateChatRoomRepository;
//
//    @Override
//    public void run() {
//        PrivateChatRoom notDetach = em.find(PrivateChatRoom.class, privateChatRoom.getId());
//        privateChatRoomRepository.delete(notDetach);
//    }
//    public RoomDestructionTask(PrivateChatRoom privateChatRoom, PrivateChatRoomRepository privateChatRoomRepository){
//        this.privateChatRoom = privateChatRoom;
//        this.privateChatRoomRepository = privateChatRoomRepository;
//    }
//}






