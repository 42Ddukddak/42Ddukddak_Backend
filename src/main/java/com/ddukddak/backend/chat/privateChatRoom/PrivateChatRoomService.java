package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class PrivateChatRoomService {

    private final PrivateChatRoomRepository chatRoomRepository;
    private final ChatTableRepository chatTableRepository;

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
