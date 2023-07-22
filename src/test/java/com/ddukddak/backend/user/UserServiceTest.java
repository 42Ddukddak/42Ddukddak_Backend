package com.ddukddak.backend.user;

import com.ddukddak.backend.chat.publicChatRoom.PublicChatRoom;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest

@Transactional
class UserServiceTest {

    @Autowired UserService userService;
    @Autowired EntityManager em;

    @Test
    void 유저생성_시_단체채팅방_확인() {

        PublicChatRoom publicChatRoom = new PublicChatRoom();

        User user = userService.create("sohyupar", publicChatRoom);

        assertEquals(user.getIntraId(), "sohyupar");
        assertEquals(user.getPublicChatRoom(), publicChatRoom);
    }
}