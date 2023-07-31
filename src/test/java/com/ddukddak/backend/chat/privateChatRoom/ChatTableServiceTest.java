package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.user.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class ChatTableServiceTest {

    @BeforeEach
    public void set() {
        User user1 = new User();
        User user2 = new User();

    }

    @Test
    void getMessageInfo() {
    }
}