package com.ddukddak.backend.chat.publicChatRoom;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@RunWith(SpringRunner.class)
@Transactional
class PublicChatRoomServiceTest {

    @Autowired PublicChatRoomService publicChatRoomService;


    @Test
    void 단체채팅방_생성() {
        PublicChatRoom pub = publicChatRoomService.create();
        assertEquals(pub.getId(), 999L);
    }


}