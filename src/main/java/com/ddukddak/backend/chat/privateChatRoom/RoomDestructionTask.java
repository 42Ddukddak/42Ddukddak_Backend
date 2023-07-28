//package com.ddukddak.backend.chat.privateChatRoom;
//
//import lombok.RequiredArgsConstructor;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.stereotype.Component;
//import org.springframework.stereotype.Service;
//
//@Component
//@RequiredArgsConstructor
//public class RoomDestructionTask implements Runnable {
//
//    @Autowired final PrivateChatRoom privateChatRoom;
//    @Autowired final PrivateChatRoomRepository privateChatRoomRepository;
//    @Override
//    public void run() {
//        privateChatRoomRepository.delete(privateChatRoom);
//    }
//}
//
//
//
//
//
//
