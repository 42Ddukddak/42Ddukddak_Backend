package com.ddukddak.backend.chat.privateChatRoom;

import com.ddukddak.backend.chat.dto.PrivateMessage;
import com.ddukddak.backend.chat.dto.PrivateRoomInfo;
import com.ddukddak.backend.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping("/roomList")
    public List<PrivateRoomInfo> showRoomList() {

        return tableService.getAllRoomInfo();
    }

    @PostMapping("/ddukddak")
    public PrivateRoomInfo createDdukddak(@RequestBody PrivateRoomInfo message) throws Exception{
        log.info(message.getLogin());
        Long tableId = userService.createPrivateChatRoom(message.getLogin(), message.getRoomName());

        return new PrivateRoomInfo(tableId, message.getRoomName(), message.getLogin(), 15L,1);
    }

    @GetMapping("/private/{id}")
    public List<PrivateMessage> showOneRoomInfo(@PathVariable Long id) {
        log.info("selected room : " + id.toString());
        return chatTableService.findMessageInfo(id);
    }
}
//    @DeleteMapping
//    @GetMapping("/ddukddak")
//    public void joinMaster() {
//        Long roomId = tableService.
//    }
//
//    }
