package com.ddukddak.backend.chat.publicChatRoom;

import com.ddukddak.backend.utils.Define;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.BeforeEach;
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
class PublicChatRoomRepositoryTest {

    @Autowired PublicChatRoomRepository chatRoom;

    @Autowired EntityManager em;

    @BeforeEach
    public void setup() {
        PublicChatRoom publicChatRoom = new PublicChatRoom();
        publicChatRoom.setId(Define.PUBLIC_CHAT_ROOM_ID);
//        publicChatRoom.setPublicContents("Public chat room~");

        Storage storage = new Storage();

        storage.setIntraId("User1");
        storage.setMessage("Hi");
        Storage storage2 = new Storage();

        storage2.setIntraId("User2");
        storage2.setMessage("Bye");

        publicChatRoom.getStorages().add(storage);
        publicChatRoom.getStorages().add(storage2);

        chatRoom.save(publicChatRoom);
        em.persist(publicChatRoom.getStorages().get(0));
        em.flush();
    }

    @Test
    public void 조회_테스트() {
        PublicChatRoom one = chatRoom.findOne(Define.PUBLIC_CHAT_ROOM_ID);
        System.out.println("id" + one.getId());
        System.out.println("message" + one.getStorages().get(0).getMessage());
        assertNotNull(one);
        assertEquals(2, one.getStorages().size());
    }

    @Test
    public void 메세지_추가_테스트() {

        PublicChatRoom one = chatRoom.findOne(Define.PUBLIC_CHAT_ROOM_ID);

        int before = one.getStorages().size();
        Storage newStorage = new Storage();

        newStorage.setIntraId("User3");
        newStorage.setMessage("배고푸당");
        one.getStorages().add(newStorage);

        chatRoom.save(one);
        em.flush();

        PublicChatRoom two = chatRoom.findOne(Define.PUBLIC_CHAT_ROOM_ID);

        assertEquals(before + 1, two.getStorages().size());

    }

}