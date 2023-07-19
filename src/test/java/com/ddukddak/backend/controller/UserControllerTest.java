package com.ddukddak.backend.controller;

import com.ddukddak.backend.domain.Reservation;
import com.ddukddak.backend.domain.ReservationStatus;
import com.ddukddak.backend.domain.User;
import com.ddukddak.backend.dto.ReservationDTO;
import com.ddukddak.backend.repository.ReservationRepository;
import com.ddukddak.backend.service.ReservationService;
import com.ddukddak.backend.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.v3.core.util.Json;
import jakarta.persistence.EntityManager;
import org.json.JSONArray;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;


@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
class UserControllerTest {
    @Autowired EntityManager em;
    @Autowired private UserService userService;
    @Autowired
    private UserController userController;
    private Reservation reservation;
    @Autowired
    private ReservationService reservationService;
    @Autowired
    private ReservationRepository reservationRepository;
    private static final Logger logger = LoggerFactory.getLogger(UserControllerTest.class);
//    @Before
//    public void setup() {
//        userController = new UserController(userService);
//    }
    @Test
    public void 유저_예약_현황() throws Exception {
        User user = new User("sohyupar");
        userService.join(user);
        Long userId = user.getId();
        logger.info("userId" + userId);

        Reservation tmp = Reservation.createReservation(user, "망한방");
        Reservation tmp2 = Reservation.createReservation(user, "안망한방");
        Reservation tmp3 = Reservation.createReservation(user, "123");
        Reservation tmp4 = Reservation.createReservation(user, "123");
        Reservation tmp5 = Reservation.createReservation(user, "123");
        Reservation tmp6 = Reservation.createReservation(user, "123");
        tmp2.setStatus(ReservationStatus.CANCEL);
        reservationRepository.save(tmp);
        reservationRepository.save(tmp2);
        reservationRepository.save(tmp3);
        reservationRepository.save(tmp4);
        reservationRepository.save(tmp5);
        reservationRepository.save(tmp6);
        tmp.setStatus(ReservationStatus.RESERVE);
        ResponseEntity responseEntity = userController.chatList(userId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());

        //responseEntity에 body에 뭐가 들어가 있는지 확인
        ObjectMapper objectMapper = new ObjectMapper();
        String responseResult = objectMapper.writeValueAsString(responseEntity.getBody());


        List<Reservation> r = reservationRepository.findAll(user);
        List<ReservationDTO> expect = new ArrayList<>();
        for(Reservation reserved : r){
            if (reserved.getStatus() == ReservationStatus.RESERVE){
                expect.add(new ReservationDTO(reserved.getChatRoomName(), reserved.getReservationTime()));
            }
        }

        String expectResult = objectMapper.writeValueAsString(expect);

        assertEquals(responseResult, expectResult);

    }
}