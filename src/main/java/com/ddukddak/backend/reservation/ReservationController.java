package com.ddukddak.backend.reservation;

import com.ddukddak.backend.chat.dto.UniformDTO;
import com.ddukddak.backend.reservation.dto.ReservationDTO;
import com.ddukddak.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/api")
public class ReservationController {

    private final ReservationService reservationService;
    private final UserService userService;


    @GetMapping("/reserved/{id}/chat-message")
    public ResponseEntity chatList(@PathVariable Long id) {
        final List<UniformDTO> result = reservationService.reservationList(id);

        return ResponseEntity.ok().body(result);
    }

    @GetMapping("/reserved/list")
    public ResponseEntity reservations(@CookieValue(name = "intraId") String intraId) {
        final List<ReservationDTO> result = userService.findReservation(intraId);

        return ResponseEntity.ok().body(result);
    }

//    @GetMapping("/reserve/{id}")
//    public List<PrivateStorage> reservationMessages(@PathVariable Long id) {
//        PrivateChatRoom room = chatTableService.findOne(id).getPrivateChatRoom();
//        return room.getPrivateStorages();
//    }
//
//    @PostMapping("/reserved/leave/{id}")
//    public void leaveReserveRoom(@RequestParam(name = "intraId") String intraId) {
//        chatTableService.leave(intraId);
//    }

    //yes ->
    @PostMapping("/reserved/{id}")
    public ResponseEntity reserve(@PathVariable Long id, @RequestParam(name = "reservedTime") String time) {
        reservationService.reservation(id, time);

        return ResponseEntity.ok().body(HttpStatus.OK);
    }

}
