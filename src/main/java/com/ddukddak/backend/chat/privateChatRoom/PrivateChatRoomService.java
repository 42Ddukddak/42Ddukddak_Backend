package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.user.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Transactional
public class PrivateChatRoomService {

    private final PrivateChatRoomRepository chatRoomRepository;

    public Long join(PrivateChatRoom privateChatRoom, User user) throws Exception{
        return chatRoomRepository.save(privateChatRoom, user);
    }

    public PrivateChatRoom findOne(Long id) {
        return chatRoomRepository.findOne(id);
    }
    public LocalDateTime findCreateTime(Long id) {
        return chatRoomRepository.findOne(id).getCreateTime();
    }
}
