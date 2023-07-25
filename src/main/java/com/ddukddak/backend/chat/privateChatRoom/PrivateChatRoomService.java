package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.chat.publicChatRoom.Storage;
import com.ddukddak.backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PrivateChatRoomService {

    private final PrivateChatRoomRepository privateChatRoomRepository;

    public Long join(PrivateChatRoom privateChatRoom, User user) throws Exception{
        if (user.isMaster() || user.isBanned())
            throw new Exception("너 못만듬;;"); //responseEntity 떤저야함
        privateChatRoomRepository.save(privateChatRoom);
        return privateChatRoom.getId();
    }

    public void saveContents(String sender, String message, String roomId) {
        PrivateChatRoom chatRoom = privateChatRoomRepository.findOne(Long.parseLong(roomId));
        PubStorage storage = new PubStorage(sender, message, roomId);

        chatRoom.addStorage(storage);
        privateChatRoomRepository.save(chatRoom);
    }


//    public void saveContents(String sender, String message, LocalDateTime time, String roomId) {
//        PrivateChatRoom chatRoom = privateChatRoomRepository.findRoomByName(roomId);
//    }
}
