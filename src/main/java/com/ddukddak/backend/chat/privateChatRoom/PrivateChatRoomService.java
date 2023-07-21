package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.user.User;
import jakarta.persistence.EntityManager;
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
}
