package com.ddukddak.backend.user;

import com.ddukddak.backend.chat.dto.UniformDTO;
import com.ddukddak.backend.chat.privateChatRoom.ChatTable;
import com.ddukddak.backend.chat.privateChatRoom.ChatTableService;
import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoom;
import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoomService;
import com.ddukddak.backend.reservation.Reservation;
import com.ddukddak.backend.reservation.Enum.ReservationStatus;
import com.ddukddak.backend.reservation.ReservationService;
import com.ddukddak.backend.reservation.dto.ReservationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;
    private final ChatTableService chatTableService;
    private final PrivateChatRoomService privateChatRoomService;
    private final ReservationService reservationService;

    @GetMapping("/chat-list")
    public ResponseEntity chatList(@CookieValue(name = "intraId") String intraId){
        List<ReservationDTO> result = reservationService.reservationList(intraId);
        return new ResponseEntity(result, HttpStatus.OK);
    }

    //방 안의 모든 유저들에게 예약 확정
    @PostMapping("/reserved/{id}")
    public ResponseEntity reserve(@PathVariable Long id) {
//        ChatTable table = chatTableService.findOne(id);
//        PrivateChatRoom privateChatRoom = table.getPrivateChatRoom();
//        List<User> users = chatTableService.findUsersInRoom(privateChatRoom.getId());
//        for (User user : users) {
//            reservationService.reservation(user, privateChatRoom.getRoomName());
//        }

        reservationService.reservation(id);
        return new ResponseEntity(HttpStatus.OK);
    }
    @PostMapping("/reserved/cancel/{id}")
    public ResponseEntity cancelReservation(@PathVariable Long id){
        reservationService.cancelReservation(id);
        return new ResponseEntity(HttpStatus.OK);
    }
}
