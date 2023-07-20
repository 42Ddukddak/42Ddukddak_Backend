package com.ddukddak.backend.user;

import com.ddukddak.backend.reservation.Reservation;
import com.ddukddak.backend.reservation.ReservationStatus;
import com.ddukddak.backend.dto.ReservationDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    @PostMapping("/")
    public String create(@RequestParam(name = "userName") String intraId){
        User user = new User(intraId);
        userService.join(user);
        return "redirect:/";
    }

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
        return new ResponseEntity<>(result, HttpStatus.OK);
    }
}
