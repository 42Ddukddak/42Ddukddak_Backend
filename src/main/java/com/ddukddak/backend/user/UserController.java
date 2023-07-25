package com.ddukddak.backend.user;

import com.ddukddak.backend.chat.privateChatRoom.ChatTableService;
import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoomService;
import com.ddukddak.backend.reservation.Reservation;
import com.ddukddak.backend.reservation.Enum.ReservationStatus;
import com.ddukddak.backend.reservation.dto.ReservationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final ChatTableService chatTableService;
    private final PrivateChatRoomService privateChatRoomService;

    @GetMapping("/chat-list")
    @ResponseBody
    public ResponseEntity chatList(Long userId){
        User user = userService.findOne(userId);
        List<Reservation> reservations = user.getReservations();
        List<ReservationDTO> result = new ArrayList<>();
        for (Reservation r : reservations){
            if (r.getStatus() == ReservationStatus.RESERVE) {
                result.add(new ReservationDTO(r.getChatRoomName(), r.getReservationTime()));
            }
        }
        return new ResponseEntity(result, HttpStatus.OK);
    }

//    @PostMapping("/ddukddak")
//    @ResponseBody
//    public ResponseEntity createDdukddak(@RequestParam Long id, @RequestParam String roomName) throws Exception{
//        User user = userService.findOne(id);
//        PrivateChatRoom privateChatRoom = new PrivateChatRoom(roomName);
//        privateChatRoomService.join(privateChatRoom, user);
//        ChatTable chatTable = new ChatTable(user, privateChatRoom);
//        chatTableService.join(chatTable);
//        user.getChatTables().add(chatTable);
//        privateChatRoom.getUsers().add(chatTable);
//        return new ResponseEntity(HttpStatus.OK);
//    }


}
