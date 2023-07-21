package com.ddukddak.backend.controller;

import com.ddukddak.backend.chat.privateChatRoom.PrivateChatRoomService;
import com.ddukddak.backend.user.User;
import com.ddukddak.backend.user.UserService;
import com.ddukddak.backend.utils.Define;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@Slf4j
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final PrivateChatRoomService privateChatRoomService;
    /*
    * 뚝딱 버튼으로 방 만들기 ->
    * 저장해야될거 -> roomId, roomName, user -> master 상태 변경
    *
    *
    * */

    @GetMapping("/ddukddak")
    public ResponseEntity create(Long userId) throws IllegalStateException {
        User user = userService.findOne(userId);
        privateChatRoomService.join(user);
        if (user.isMaster())
            throw new IllegalStateException("이미 방장입니다");
        user.setMaster(true);

        return new ResponseEntity(Define.PUBLIC_ROOM_ID, HttpStatus.OK);
    }
}