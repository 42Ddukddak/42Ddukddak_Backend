package com.ddukddak.backend.service;

import com.ddukddak.backend.domain.*;
import com.ddukddak.backend.repository.ReservationRepository;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class ReservationServiceTest {

    @Autowired EntityManager em;
    @Autowired ReservationService reservationService;
    @Autowired ReservationRepository reservationRepository;





    private ChatTable createChatTable(User user, PrivateChatRoom privateChatRoom){
        ChatTable chatTable = new ChatTable();
        chatTable.setUser(user);
        chatTable.setPrivateChatRoom(privateChatRoom);
        em.persist(chatTable);
        return chatTable;
    }
    private PrivateChatRoom createRoom(){
        PrivateChatRoom privateChatRoom = new PrivateChatRoom();
        privateChatRoom.setRoomName("뚝뚝딱");
        privateChatRoom.setCreateTime(LocalDateTime.now());
        privateChatRoom.setRestTime(15L);
        em.persist(privateChatRoom);
        return privateChatRoom;
    }

    private User createUser(){
        User user = new User("gd");
////        user.setIntraId("suhwpark");
//        user.setBanned(false);
//        user.setReportNumber(0L);
//        user.setReportTime(LocalDateTime.now());
//        user.setMaster(false);
        em.persist(user);
        return user;
    }

    @Test
//  @Rollback(value = false)
    void 예약_확인() {
        User user = createUser();
        PrivateChatRoom privateChatRoom = createRoom();
        ChatTable chatTable = createChatTable(user, privateChatRoom);

        Long reservedId = reservationService.reservation(chatTable.getId());
        Reservation getReservation = reservationRepository.findOne(reservedId);
//-
        assertEquals("예약 상태 비교", ReservationStatus.RESERVE, getReservation.getStatus());
        assertEquals("예약 아이디 같은지 DB 확인", reservedId, getReservation.getId());
        assertEquals("예약 제목이 같은지 확인", privateChatRoom.getRoomName(), getReservation.getChatRoomName());
        assertEquals("user가 같나요?", user.getIntraId(), getReservation.getUser().getIntraId());
    }
    @Test
    void 예약_취소_확인() {
        User user = createUser();
        PrivateChatRoom privateChatRoom = createRoom();
        ChatTable chatTable = createChatTable(user, privateChatRoom);

        Long reservedId = reservationService.reservation(chatTable.getId());
        Reservation getReservation = reservationRepository.findOne(reservedId);
        getReservation.cancel();

        assertEquals("예약 상태 비교", ReservationStatus.CANCEL, getReservation.getStatus());

    }
}
