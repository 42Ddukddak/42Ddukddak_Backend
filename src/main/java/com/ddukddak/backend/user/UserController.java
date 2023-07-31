package com.ddukddak.backend.user;

import com.ddukddak.backend.chat.dto.UniformDTO;
import com.ddukddak.backend.chat.privateChatRoom.ChatTable;
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

@RestController
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

    //방 안의 모든 유저들에게 예약 확정
    @PostMapping("/api/reserved/{id}")
    public ResponseEntity reserve(@PathVariable Long id, @RequestBody UniformDTO uniformDTO) {
        ChatTable table = chatTableService.findOne(id);

        return new ResponseEntity(HttpStatus.OK);
    }
}
