package com.ddukddak.backend.user;

import com.ddukddak.backend.chat.privateChatRoom.*;
import com.ddukddak.backend.chat.publicChatRoom.PublicChatRoom;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Duration;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@Service
@RequiredArgsConstructor
@Transactional
public class UserService {

    private final UserRepository userRepository;
    private final PrivateChatRoomRepository privateChatRoomRepository;
    private final ChatTableRepository chatTableRepository;
    private final TaskScheduler taskScheduler;

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
        user.setMaster(true);
        PrivateChatRoom privateChatRoom = new PrivateChatRoom(roomName);
        privateChatRoomRepository.save(privateChatRoom, user);
//        if (privateChatRoom.getExpirationTime() != null) {
//            Duration duration = Duration.between(privateChatRoom.getCreateTime(), privateChatRoom.getExpirationTime());
//            long delayInMillis = duration.toMillis();
//
//            taskScheduler.schedule(new RoomDestructionTask(privateChatRoom, privateChatRoomRepository), new Date(System.currentTimeMillis() + delayInMillis));
//        }
        ChatTable chatTable = ChatTable.createChatTable(user, privateChatRoom);
        chatTable.setHost(user.getIntraId());
        chatTableRepository.save(chatTable);
        user.getChatTables().add(chatTable);

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
