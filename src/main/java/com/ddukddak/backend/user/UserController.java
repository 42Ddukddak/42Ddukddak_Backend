package com.ddukddak.backend.user;

import com.ddukddak.backend.chat.privateChatRoom.ChatTableService;
import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoomService;
import com.ddukddak.backend.reservation.ReservationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
@Slf4j
public class UserController {

    private final UserService userService;
    private final ChatTableService chatTableService;
    private final PrivateChatRoomService privateChatRoomService;
    private final ReservationService reservationService;


    //    방 안의 모든 유저들에게 예약 확정

    @PostMapping("/reserved/cancel/{id}")
    public ResponseEntity cancelReservation(@PathVariable Long id) {
        reservationService.cancelReservation(id);
        return ResponseEntity.ok(HttpStatus.OK);
    }
}
