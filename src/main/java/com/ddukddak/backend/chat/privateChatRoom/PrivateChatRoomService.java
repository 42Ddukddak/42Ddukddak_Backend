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

    public Long join(PrivateChatRoom privateChatRoom, User user) throws Exception{
        return chatRoomRepository.save(privateChatRoom, user);
    }

    public void saveContents(String sender, String message, String roomId) {
        PrivateChatRoom chatRoom = chatRoomRepository.findOne(Long.parseLong(roomId));
//        PubStorage storage = new PubStorage(sender, message, roomId);

//        chatRoom.addStorage(storage);
//        chatRoomRepository.save(chatRoom);
    }


//    public void saveContents(String sender, String message, LocalDateTime time, String roomId) {
//        PrivateChatRoom chatRoom = privateChatRoomRepository.findRoomByName(roomId);
//    }
}
