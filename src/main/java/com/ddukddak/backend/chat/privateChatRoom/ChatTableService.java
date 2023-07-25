package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.chat.privateChatRoom.ChatTable;
import com.ddukddak.backend.chat.privateChatRoom.ChatTableRepository;
import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoom;
import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoomRepository;
import com.ddukddak.backend.user.User;
import com.ddukddak.backend.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional()
public class ChatTableService {

    private final ChatTableRepository chatTableRepository;
    private final UserRepository userRepository;
    private final PrivateChatRoomRepository privateChatRoomRepository;

    @Transactional
    public Long join(ChatTable chatTable){
        chatTableRepository.save(chatTable);
        return chatTable.getId();
    }

    public void create(User user, PrivateChatRoom privateChatRoom) {
        ChatTable chatTable = ChatTable.createChatTable(user, privateChatRoom);
        user = userRepository.findOne(user.getId());

    }


    public ChatTable findOne(Long chatTableId){
        return chatTableRepository.findOne(chatTableId);
    }
}
