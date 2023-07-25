package com.ddukddak.backend.user;

import com.ddukddak.backend.chat.ChatTable;
import com.ddukddak.backend.chat.ChatTableRepository;
import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoom;
import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoomRepository;
import com.ddukddak.backend.chat.publicChatRoom.PublicChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PrivateChatRoomRepository privateChatRoomRepository;
    private final ChatTableRepository chatTableRepository;

    public Long join(User user) {
        userRepository.save(user);
        return user.getId();
    }

    public User createPublicChatRoom(String intraId, PublicChatRoom publicChatRoom){
        User user = new User(intraId, publicChatRoom);
        userRepository.save(user);
        return user;
    }

    public Long createPrivateChatRoom(String intraId, String roomName) throws Exception{
        User user = userRepository.findByName(intraId);
        PrivateChatRoom privateChatRoom = new PrivateChatRoom(roomName);
        privateChatRoomRepository.save(privateChatRoom, user);
        ChatTable chatTable = ChatTable.createChatTable(user, privateChatRoom);
        chatTableRepository.save(chatTable);
        return chatTable.getId();
    }

    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }

    public User findByName(String name){
        return userRepository.findByName(name);
    }
}
