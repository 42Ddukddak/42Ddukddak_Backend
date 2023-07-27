package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.chat.dto.PrivateMessage;
import com.ddukddak.backend.chat.dto.PrivateRoomInfo;
import com.ddukddak.backend.chat.dto.UniformDTO;
import com.ddukddak.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/chat")
@Slf4j
public class PrivateChatroomController {

    private final UserService userService;
    private final ChatTableService tableService;
    private final PrivateChatRoomService privateChatRoomService;
    private final ChatTableService chatTableService;
    private int person;

    @GetMapping("/roomList")
    public List<PrivateRoomInfo> showRoomList() {

        return tableService.getAllRoomInfo();
    }

    //새로운 방 만들기
    @PostMapping("/ddukddak")
    public PrivateRoomInfo createDdukddak(@RequestBody PrivateRoomInfo message) throws Exception{
        log.info(message.getLogin());
        Long tableId = userService.createPrivateChatRoom(message.getLogin(), message.getRoomName());

        return new PrivateRoomInfo(tableId, message.getRoomName(), message.getLogin(), 15L,1);
    }

    //만들어진 방에 들어가기
    @GetMapping("/private/{id}")
    public List<UniformDTO> showOneRoomInfo(@PathVariable Long id) {
        log.info("selected room : " + id.toString());
        return chatTableService.getMessageInfo(id);
    }
}

