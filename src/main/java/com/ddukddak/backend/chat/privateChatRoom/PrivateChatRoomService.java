package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.user.User;
import com.ddukddak.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PrivateChatRoomService {

    private final PrivateChatRoomRepository chatRoomRepository;
    private final ChatTableRepository chatTableRepository;
    private final UserRepository userRepository;

    public Long join(PrivateChatRoom privateChatRoom, User user) throws Exception{
        return chatRoomRepository.save(privateChatRoom, user);
    }

    public PrivateChatRoom findOne(Long id) {
        return chatRoomRepository.findOne(id);
    }
    public LocalDateTime findCreateTime(Long id) {
        return chatRoomRepository.findOne(id).getCreateTime();
    }

    public List<User> getAllUsersInChatRoom(Long id) {
        PrivateChatRoom room = chatRoomRepository.findOne(id);
        List<User> usersInChatRoom = new ArrayList<>();

        for (ChatTable table : room.getChatTables()) {
            usersInChatRoom.add(table.getUser());
        }
        return usersInChatRoom;
    }
}
