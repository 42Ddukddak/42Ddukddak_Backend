package com.ddukddak.backend.user;

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

    @Transactional
    public Long join(User user) {
        userRepository.save(user);
        return user.getId();
    }

    public User create(String intraId, PublicChatRoom publicChatRoom){
        User user = new User(intraId, publicChatRoom);
        userRepository.save(user);
        return user;
    }
    public List<User> findUsers() {
        return userRepository.findAll();
    }

    public User findOne(Long userId) {
        return userRepository.findOne(userId);
    }
}
