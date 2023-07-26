package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.chat.dto.PrivateRoomInfo;
import com.ddukddak.backend.user.User;
import com.ddukddak.backend.user.UserRepository;
import com.ddukddak.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PrivateChatRoomService {

    private final PrivateChatRoomRepository chatRoomRepository;

    public Long join(PrivateChatRoom privateChatRoom, User user) throws Exception{
        return chatRoomRepository.save(privateChatRoom, user);
    }



//    public void saveContents(String sender, String message, String roomId) {
//        ChatTableRepository chatTable = chatTableRepository.findOne(Long.parseLong(roomId));
//        PrivateStorage privateStorage = new PrivateStorage(sender, message, roomId);
//
//        chatTable.addStorage(privateStorage);
////        chatRoomRepository.save(chatRoom);
//    }


//    public void saveContents(String sender, String message, LocalDateTime time, String roomId) {
//        PrivateChatRoom chatRoom = privateChatRoomRepository.findRoomByName(roomId);
//    }
}
